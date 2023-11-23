package mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mouvements {
    private int id;

    private Magasins magasin;

    private Articles article;

    private double quantite;

    @JsonProperty("prix_unitaire")
    private Double prixUnitaire;

    @JsonProperty("date_mouvement")
    private String dateMouvement;

    private int etat;

    public Mouvements() {
    }

    public Mouvements(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(String dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
