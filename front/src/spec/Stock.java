package spec;

import com.fasterxml.jackson.annotation.JsonProperty;

import mapping.Articles;
import mapping.Magasins;

public class Stock {
    private Magasins magasin;

    private Articles article;

    @JsonProperty("quantite_intiale")
    private double quantiteInitiale;

    @JsonProperty("quantite_restante")
    private double quantiteRestante;

    @JsonProperty("prix_unitaire")
    private double prixUnitaire;

    @JsonProperty("montant_total")
    private double montantTotal;

    public Stock() {
    }

    public Stock(Magasins magasin, Articles article, double quantiteInitiale, double quantiteRestante,
            double prixUnitaire, double montantTotal) {
        this.magasin = magasin;
        this.article = article;
        this.quantiteInitiale = quantiteInitiale;
        this.quantiteRestante = quantiteRestante;
        this.prixUnitaire = prixUnitaire;
        this.montantTotal = montantTotal;
    }

    public Magasins getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasins magasin) {
        this.magasin = magasin;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    public double getQuantiteInitiale() {
        return quantiteInitiale;
    }

    public void setQuantiteInitiale(double quantiteInitiale) {
        this.quantiteInitiale = quantiteInitiale;
    }

    public double getQuantiteRestante() {
        return quantiteRestante;
    }

    public void setQuantiteRestante(double quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

}
