package mapping;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Query;

@Entity
public class Articles {
    @Id
    private int id;

    private String numero;

    private String nom;

    @OneToOne
    @JoinColumn(name = "id_unite")
    private Unites unite;

    @OneToOne
    @JoinColumn(name = "id_methode")
    private Methodes methode;

    public Articles() {
    }

    public Articles(int id) {
        this.id = id;
    }

    public static List<Articles> getAll(EntityManager entityManager) {
        String sql = "select * from articles";
        Query query = entityManager.createNativeQuery(sql, Articles.class);
        return query.getResultList();
    }

    public static Articles getById(EntityManager entityManager, int id) {
        String sql = "select * from articles where id = " + id;
        Query query = entityManager.createNativeQuery(sql, Articles.class);
        List<Articles> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Unites getUnite() {
        return unite;
    }

    public void setUnite(Unites unite) {
        this.unite = unite;
    }

    public Methodes getMethode() {
        return methode;
    }

    public void setMethode(Methodes methode) {
        this.methode = methode;
    }

}