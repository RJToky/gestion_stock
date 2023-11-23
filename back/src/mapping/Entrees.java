package mapping;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Entity
@DiscriminatorValue(value = "1")
public class Entrees extends Mouvements {

    public Entrees() {
    }

    public Entrees(int idMagasin, int idArticle, double quantite, double prixUnitaire, String dateMouvement) {
        super(idMagasin, idArticle, quantite, prixUnitaire, dateMouvement);
    }

    public static List<Entrees> getAll(EntityManager entityManager) {
        String sql = "select * from mouvements where id_type_mouvement = 1 order by date_mouvement asc";
        Query query = entityManager.createNativeQuery(sql, Entrees.class);
        return query.getResultList();
    }

    public static Entrees getById(EntityManager entityManager, int id) {
        String sql = "select * from mouvements where id = " + id
                + " and id_type_mouvement = 1";
        Query query = entityManager.createNativeQuery(sql, Entrees.class);
        List<Entrees> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public static List<Entrees> getByIdMagasin(EntityManager entityManager, int idMagasin) {
        String sql = "select * from mouvements where id_magasin = " + idMagasin
                + " and id_type_mouvement = 1 order by date_mouvement asc";
        Query query = entityManager.createNativeQuery(sql, Entrees.class);
        return query.getResultList();
    }

    public static List<Entrees> getByIdArticle(EntityManager entityManager, int idArticle) {
        String sql = "select * from mouvements where id_article = " + idArticle
                + " and id_type_mouvement = 1 order by date_mouvement asc";
        Query query = entityManager.createNativeQuery(sql, Entrees.class);
        return query.getResultList();
    }

    public static List<Entrees> getByIdMagasinAndIdArticle(EntityManager entityManager, int idMagasin, int idArticle) {
        String sql = "select * from mouvements where id_magasin = " + idArticle
                + "  and id_article = " + idMagasin
                + " and id_type_mouvement = 1 order by date_mouvement asc";
        Query query = entityManager.createNativeQuery(sql, Entrees.class);
        return query.getResultList();
    }

    public void insert(EntityManager entityManager) {
        String sql = "insert into mouvements values (default, 1, :id_magasin, :id_article, :quantite, :prix_unitaire, :date_mouvement, 20)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_magasin", this.getMagasin().getId());
        query.setParameter("id_article", this.getArticle().getId());
        query.setParameter("quantite", this.getQuantite());
        query.setParameter("prix_unitaire", this.getPrixUnitaire());
        query.setParameter("date_mouvement", this.getDateMouvement());
        query.executeUpdate();
    }

    public double calculQuantiteRestanteByEntree(EntityManager entityManager, String date) {
        String sql = """
                    select (
                        select
                            coalesce(quantite, 0)
                        from
                            mouvements as entrees
                        where
                            id = :id_mouvement_entree
                            and date_mouvement <= cast(:date as date)
                        ) - (
                        select
                            coalesce(sum(sd.quantite), 0)
                        from
                            sortie_decomposes as sd
                        join mouvements as sorties on sorties.id = sd.id_mouvement_sortie
                        join validations as v on sorties.id = v.id_mouvement
                        where
                            sd.id_mouvement_entree = :id_mouvement_entree
                            and v.date_validation <= cast(:date as date)
                    ) as quantite_restante
                """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_mouvement_entree", this.getId());
        query.setParameter("date", date);
        if (query.getSingleResult() != null) {
            return Double.parseDouble(query.getSingleResult().toString());
        }
        return 0.0;
    }

}
