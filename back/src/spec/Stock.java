package spec;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import mapping.Articles;
import mapping.Entrees;
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

    @JsonIgnore
    private String dateDebut;

    @JsonIgnore
    private String dateFin;

    public Stock(Magasins magasin, Articles article, double quantiteInitiale, double quantiteRestante,
            double prixUnitaire, double montantTotal) {
        this.magasin = magasin;
        this.article = article;
        this.quantiteInitiale = quantiteInitiale;
        this.quantiteRestante = quantiteRestante;
        this.prixUnitaire = prixUnitaire;
        this.montantTotal = montantTotal;
    }

    public Stock(String dateDebut, String dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Stock() {
    }

    public double calculQuantiteRestante(EntityManager entityManager, String date) {
        String sql = """
                    select (
                        select
                            coalesce(sum(quantite), 0)
                        from
                            mouvements as entrees
                        where
                            id_type_mouvement = 1
                            and id_magasin = :id_magasin
                            and id_article = :id_article
                            and date_mouvement <= cast(:date as date)
                        ) - (
                        select
                            coalesce(sum(quantite), 0)
                        from
                            mouvements as sorties
                        join
                            validations v on sorties.id = v.id_mouvement
                        where
                            id_type_mouvement = 2
                            and id_magasin = :id_magasin
                            and id_article = :id_article
                            and v.date_validation <= cast(:date as date)
                    ) as quantite_restante
                """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_magasin", this.getMagasin().getId());
        query.setParameter("id_article", this.getArticle().getId());
        query.setParameter("date", date);

        return Double.parseDouble(query.getSingleResult().toString());
    }

    public double calculMontantTotal(EntityManager entityManager) {
        List<Entrees> entrees = Entrees.getByIdMagasinAndIdArticle(entityManager,
                this.getMagasin().getId(), this.getArticle().getId());

        double res = 0.0;
        for (Entrees entree : entrees) {
            res += entree.calculQuantiteRestanteByEntree(entityManager, this.getDateFin())
                    * entree.getPrixUnitaire();
        }
        return res;
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

    public void setQuantiteInitiale(EntityManager entityManager) {
        this.setQuantiteInitiale(this.calculQuantiteRestante(entityManager, this.getDateDebut()));
    }

    public double getQuantiteRestante() {
        return quantiteRestante;
    }

    public void setQuantiteRestante(double quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public void setQuantiteRestante(EntityManager entityManager) {
        this.setQuantiteRestante(this.calculQuantiteRestante(entityManager, this.getDateFin()));
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setPrixUnitaire() {
        this.setPrixUnitaire(Double.isNaN(this.getMontantTotal() / this.getQuantiteRestante()) ? 0.0
                : this.getMontantTotal() / this.getQuantiteRestante());
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public void setMontantTotal(EntityManager entityManager) {
        this.setMontantTotal(this.calculMontantTotal(entityManager));
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

}
