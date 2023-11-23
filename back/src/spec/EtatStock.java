package spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EntityManager;
import mapping.Articles;
import mapping.Magasins;

public class EtatStock {
    @JsonProperty("date_debut")
    private String dateDebut;

    @JsonProperty("date_fin")
    private String dateFin;

    private ArrayList<Stock> stocks;

    @JsonProperty("montant_total")
    private double montantTotal;

    public EtatStock(EntityManager entityManager, String dateDebut, String dateFin, int idMagasin) {
        this.setDateDebut(dateDebut);
        this.setDateFin(dateFin);
        this.setStocksAndMontantTotal(entityManager, dateDebut, dateFin, idMagasin);
    }

    public void setStocksAndMontantTotal(EntityManager entityManager, String dateDebut, String dateFin, int idMagasin) {
        List<Articles> articles = Articles.getAll(entityManager);

        ArrayList<Stock> stocks = new ArrayList<Stock>();
        double montantTotal = 0.0;

        for (Articles article : articles) {

            // idMagasin = 0 =>> Tous les magasins
            if (idMagasin == 0) {
                List<Magasins> magasins = Magasins.getAll(entityManager);
                for (Magasins magasin : magasins) {
                    Stock stock = new Stock(dateDebut, dateFin);
                    stock.setMagasin(magasin);
                    stock.setArticle(article);
                    stock.setQuantiteInitiale(entityManager);
                    stock.setQuantiteRestante(entityManager);
                    stock.setMontantTotal(entityManager);
                    stock.setPrixUnitaire();

                    if (stock.getQuantiteInitiale() != 0 || stock.getQuantiteRestante() != 0) {
                        stocks.add(stock);
                        montantTotal += stock.getMontantTotal();
                    }
                }
            } else {
                Stock stock = new Stock(dateDebut, dateFin);
                stock.setMagasin(Magasins.getById(entityManager, idMagasin));
                stock.setArticle(article);
                stock.setQuantiteInitiale(entityManager);
                stock.setQuantiteRestante(entityManager);
                stock.setMontantTotal(entityManager);
                stock.setPrixUnitaire();

                if (stock.getQuantiteInitiale() != 0 || stock.getQuantiteRestante() != 0) {
                    stocks.add(stock);
                    montantTotal += stock.getMontantTotal();
                }
            }
        }
        this.setStocks(stocks);
        this.setMontantTotal(montantTotal);
    }

    public EtatStock() {
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