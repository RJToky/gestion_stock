package mapping;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.Query;

@Entity
public class Unites {
    @Id
    private int id;

    private String nom;

    private String abrev;

    public Unites() {
    }

    public static List<Unites> getAll(EntityManager entityManager) {
        String sql = "select * from unites";
        Query query = entityManager.createNativeQuery(sql, Unites.class);
        return query.getResultList();
    }

    public static Unites getById(EntityManager entityManager, int id) {
        String sql = "select * from unites where id = " + id;
        Query query = entityManager.createNativeQuery(sql, Unites.class);
        List<Unites> res = query.getResultList();
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

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

}