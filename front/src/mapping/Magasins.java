package mapping;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Api;

public class Magasins {
    private int id;
    private String nom;

    public Magasins() {
    }

    public static List<Magasins> getAll() {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/magasins", "GET");
            ObjectMapper mapper = new ObjectMapper();
            List<Magasins> res = mapper.readValue(data, new TypeReference<List<Magasins>>() {
            });
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Magasins getById(int id) {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/magasins/" + id, "GET");
            ObjectMapper mapper = new ObjectMapper();
            Magasins res = mapper.readValue(data, Magasins.class);
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
