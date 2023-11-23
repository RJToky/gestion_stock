package mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Api;

public class Sorties extends Mouvements {
    @JsonProperty("is_valid")
    private boolean isValid;

    public Sorties() {
    }

    public static List<Sorties> getAll() {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/sorties", "GET");
            ObjectMapper mapper = new ObjectMapper();
            List<Sorties> res = mapper.readValue(data, new TypeReference<List<Sorties>>() {
            });
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Sorties> getAllOfEtat(int etat) {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/sorties?etat=" + etat, "GET");
            ObjectMapper mapper = new ObjectMapper();
            List<Sorties> res = mapper.readValue(data, new TypeReference<List<Sorties>>() {
            });
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Sorties getById(int id) {
        try {
            String data = Api.fetch("http://localhost:8080/back_stock/app/sorties/" + id, "GET");
            ObjectMapper mapper = new ObjectMapper();
            Sorties res = mapper.readValue(data, Sorties.class);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

}