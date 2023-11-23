package mapping;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Api;

public class Articles {
    private int id;
    private String numero;
    private String nom;
    private Unites unite;
    private Methodes methode;

    public Articles() {
    }

    public static List<Articles> getAll() {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/articles", "GET");
            ObjectMapper mapper = new ObjectMapper();
            List<Articles> res = mapper.readValue(data, new TypeReference<List<Articles>>() {
            });
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Articles getById(int id) {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/articles/" + id, "GET");
            ObjectMapper mapper = new ObjectMapper();
            Articles res = mapper.readValue(data, Articles.class);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public Methodes getMethode() {
        return methode;
    }

    public void setMethode(Methodes methode) {
        this.methode = methode;
    }

    public Unites getUnite() {
        return unite;
    }

    public void setUnite(Unites unite) {
        this.unite = unite;
    }

}