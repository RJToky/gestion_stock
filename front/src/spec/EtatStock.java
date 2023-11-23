package spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mapping.Articles;
import mapping.Magasins;
import util.Api;

public class EtatStock {
    @JsonProperty("date_debut")
    private String dateDebut;

    @JsonProperty("date_fin")
    private String dateFin;

    private ArrayList<Stock> stocks;

    @JsonProperty("montant_total")
    private double montantTotal;

    public EtatStock(String dateDebut, String dateFin, int idMagasin) {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/etat_stock?date_debut=" + dateDebut
                    + "&date_fin=" + dateFin + "&id_magasin=" + idMagasin, "GET");
            ObjectMapper mapper = new ObjectMapper();

            EtatStock res = mapper.readValue(data, EtatStock.class);
            this.setDateDebut(res.getDateDebut());
            this.setDateFin(res.getDateFin());
            this.setStocks(res.getStocks());
            this.setMontantTotal(res.getMontantTotal());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EtatStock() {
        this.setDateDebut("12/12/2022");
        this.setDateFin("31/12/2022");

        List<Magasins> magasins = Magasins.getAll();
        List<Articles> articles = Articles.getAll();

        ArrayList<Stock> stocks = new ArrayList<Stock>();
        stocks.add(new Stock(magasins.get(0), articles.get(0), 400, 100, 3500, 350000));
        stocks.add(new Stock(magasins.get(0), articles.get(1), 500, 400, 3000, 1200000));

        this.setStocks(stocks);
        this.setMontantTotal(1550000);
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

}