package mapping;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Api;

public class Unites {
    private int id;
    private String nom;
    private String abrev;

    public Unites() {
    }

    public Unites(int id) {
        this.id = id;
    }

    public static List<Unites> getAll() {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/unites", "GET");
            ObjectMapper mapper = new ObjectMapper();
            List<Unites> res = mapper.readValue(data, new TypeReference<List<Unites>>() {
            });
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Unites getById(int id) {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/unites/" + id, "GET");
            ObjectMapper mapper = new ObjectMapper();
            Unites res = mapper.readValue(data, Unites.class);
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

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

}