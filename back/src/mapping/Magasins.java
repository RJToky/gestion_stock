package mapping;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.Query;

@Entity
public class Magasins {
    @Id
    private int id;

    private String nom;

    public Magasins() {
    }

    public Magasins(int id) {
        this.id = id;
    }

    public static List<Magasins> getAll(EntityManager entityManager) {
        String sql = "select * from magasins";
        Query query = entityManager.createNativeQuery(sql, Magasins.class);
        return query.getResultList();
    }

    public static Magasins getById(EntityManager entityManager, int id) {
        String sql = "select * from magasins where id = " + id;
        Query query = entityManager.createNativeQuery(sql, Magasins.class);
        List<Magasins> res = (List<Magasins>) query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
