package mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Query;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "id_type_mouvement", discriminatorType = DiscriminatorType.INTEGER)
public class Mouvements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_magasin")
    private Magasins magasin;

    @OneToOne
    @JoinColumn(name = "id_article")
    private Articles article;

    private double quantite;

    @JsonProperty("prix_unitaire")
    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @JsonProperty("date_mouvement")
    @Column(name = "date_mouvement")
    private String dateMouvement;

    private int etat;

    public Mouvements(int idMagasin, int idArticle, double quantite, Double prixUnitaire, String dateMouvement) {
        this.setMagasin(new Magasins(idMagasin));
        this.setArticle(new Articles(idArticle));
        this.setQuantite(quantite);
        this.setPrixUnitaire(prixUnitaire);
        this.setDateMouvement(dateMouvement);
    }

    public double calculQuantiteRestante(EntityManager entityManager) {
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
        query.setParameter("date", this.getDateMouvement());

        return Double.parseDouble(query.getSingleResult().toString());
    }

    public Mouvements(int id) {
        this.id = id;
    }

    public Mouvements() {
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

    public void setEtat(EntityManager entityManager, int etat) {
        String sql = "update mouvements set etat = :etat where id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("etat", etat);
        query.setParameter("id", this.getId());
        query.executeUpdate();
    }

    public void setQuantite(EntityManager entityManager, double quantite) {
        String sql = "update mouvements set quantite = :quantite where id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("quantite", quantite);
        query.setParameter("id", this.getId());
        query.executeUpdate();
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
