package mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Transient;
import spec.Util;

@Entity
@DiscriminatorValue(value = "2")
public class Sorties extends Mouvements {
    @Transient
    @JsonIgnore
    private String dateValidation;

    @Transient
    @JsonProperty("is_valid")
    private boolean isValid;

    public Sorties() {
    }

    public Sorties(int id) {
        super(id);
    }

    public Sorties(int idMagasin, int idArticle, double quantite, String dateMouvement) {
        super(idMagasin, idArticle, quantite, null, dateMouvement);
    }

    public void saisir(EntityManager entityManager) throws Exception {
        double quantiteSortie = this.getQuantite();
        double quantiteRestante = this.calculQuantiteRestante(entityManager);

        if (quantiteSortie <= 0) {
            throw new Exception("Tsy afaka manala quantité 0 na latsaky ny 0");
        }
        if (quantiteRestante >= quantiteSortie) {
            if (this.checkDateMouvement(entityManager)) {
                this.insert(entityManager);
            } else {
                throw new Exception("Tsy mety ny date de saisie");
            }
        } else {
            throw new Exception("Tsy ampy intsony ny stock");
        }
    }

    public void valider(EntityManager entityManager, double quantiteSortie) throws Exception {
        double quantiteRestante = this.calculQuantiteRestante(entityManager);

        if (quantiteSortie <= 0) {
            throw new Exception("Tsy afaka manala quantité 0 na latsaky ny 0");
        }
        if (quantiteRestante >= quantiteSortie) {
            if (this.checkDateMouvement(entityManager) && this.checkDateValidation(entityManager)) {
                Sorties sortie = Sorties.getById(entityManager, this.getId());
                sortie.setQuantite(entityManager, quantiteSortie);
                sortie.setEtat(entityManager, 20);

                Validations validation = new Validations(this.getId(), this.getDateValidation());
                validation.insert(entityManager);

                SortieDecomposes sortieDecomposes = new SortieDecomposes(sortie);
                sortieDecomposes.decomposer(entityManager);

            } else {
                throw new Exception("Tsy mety ny date de validation");
            }
        } else {
            throw new Exception("Tsy ampy intsony ny stock");
        }
    }

    public void annuler(EntityManager entityManager) {
        Sorties sortie = Sorties.getById(entityManager, this.getId());
        sortie.setEtat(entityManager, 1);
    }

    public boolean checkDateMouvement(EntityManager entityManager) {
        String lastDateMouvement = this.getLastDateMouvement(entityManager);
        String lastDateValidation = this.getLastDateValidation(entityManager);

        if (lastDateMouvement != "") {
            // return (Util.compareDate(this.getDateMouvement(), lastDateMouvement) >= 0);
            return (Util.compareDate(this.getDateMouvement(), lastDateMouvement) >= 0)
                    && (Util.compareDate(this.getDateMouvement(), lastDateValidation) >= 0);
        }
        return true;
    }

    public boolean checkDateValidation(EntityManager entityManager) {
        String lastDateValidation = this.getLastDateValidation(entityManager);

        if (lastDateValidation != "") {
            return (Util.compareDate(this.getDateValidation(), this.getDateMouvement()) >= 0)
                    && (Util.compareDate(this.getDateValidation(), lastDateValidation) >= 0);
        }
        return (Util.compareDate(this.getDateValidation(), this.getDateMouvement()) >= 0);
    }

    public static List<Sorties> getAll(EntityManager entityManager) {
        String sql = "select * from mouvements where id_type_mouvement = 2 order by date_mouvement asc";
        Query query = entityManager.createNativeQuery(sql, Sorties.class);
        return query.getResultList();
    }

    public static List<Sorties> getAllOfEtat(EntityManager entityManager, int etat) {
        String sql = "select * from mouvements where id_type_mouvement = 2 and etat = " + etat
                + " order by date_mouvement asc";
        Query query = entityManager.createNativeQuery(sql, Sorties.class);
        List<Sorties> sorties = query.getResultList();
        for (Sorties sortie : sorties) {
            boolean valid = sortie.checkDateMouvement(entityManager);
            sortie.setValid(valid);
        }
        return query.getResultList();
    }

    public static Sorties getById(EntityManager entityManager, int id) {
        String sql = "select * from mouvements where id = " + id
                + " and id_type_mouvement = 2";
        Query query = entityManager.createNativeQuery(sql, Sorties.class);
        List<Sorties> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public void insert(EntityManager entityManager) {
        String sql = "insert into mouvements values (default, 2, :id_magasin, :id_article, :quantite, :prix_unitaire, cast(:date_mouvement as date), 10)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_magasin", this.getMagasin().getId());
        query.setParameter("id_article", this.getArticle().getId());
        query.setParameter("quantite", this.getQuantite());
        query.setParameter("prix_unitaire", this.getPrixUnitaire());
        query.setParameter("date_mouvement", this.getDateMouvement());
        query.executeUpdate();
    }

    public String getLastDateMouvement(EntityManager entityManager) {
        String sql = "select date_mouvement from mouvements where id_magasin = :id_magasin and id_article = :id_article and id_type_mouvement = 2 and etat = 20 order by date_mouvement desc limit 1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_magasin", this.getMagasin().getId());
        query.setParameter("id_article", this.getArticle().getId());
        try {
            return query.getSingleResult().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String getLastDateValidation(EntityManager entityManager) {
        String sql = "select v.date_validation from validations v join mouvements m on m.id = v.id_mouvement where id_magasin = :id_magasin and id_article = :id_article order by v.date_validation desc limit 1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_magasin", this.getMagasin().getId());
        query.setParameter("id_article", this.getArticle().getId());
        try {
            return query.getSingleResult().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(String dateValidation) {
        this.dateValidation = dateValidation;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

}