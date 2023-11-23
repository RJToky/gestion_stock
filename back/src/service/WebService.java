package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import mapping.Articles;
import mapping.Entrees;
import mapping.Unites;
import spec.EtatStock;
import mapping.Magasins;
import mapping.SortieDecomposes;
import mapping.Sorties;

@Stateless
@Path("/")
public class WebService {
    @PersistenceContext(name = "persistenceUnitStock")
    private EntityManager entityManager;

    @GET
    @Path("/unites")
    public String getUnites() {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Unites.getAll(entityManager));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/unites/{id}")
    public String getUniteById(@PathParam("id") int id) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Unites.getById(entityManager, id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/articles")
    public String getArticles() {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Articles.getAll(entityManager));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/articles/{id}")
    public String getArticleById(@PathParam("id") int id) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Articles.getById(entityManager, id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/entrees")
    public String getEntrees(@QueryParam("id_magasin") int idMagasin, @QueryParam("id_article") int idArticle) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Entrees.getAll(entityManager));
            if (idMagasin != 0 && idArticle != 0) {
                res = mapper
                        .writeValueAsString(Entrees.getByIdMagasinAndIdArticle(entityManager,
                                idMagasin, idArticle));
            } else if (idMagasin != 0) {
                res = mapper.writeValueAsString(Entrees.getByIdMagasin(entityManager,
                        idMagasin));
            } else if (idArticle != 0) {
                res = mapper.writeValueAsString(Entrees.getByIdArticle(entityManager,
                        idArticle));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/entrees/{id}")
    public String getEntreeById(@PathParam("id") int id) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Entrees.getById(entityManager, id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/sorties")
    public String getSorties(@QueryParam("etat") int etat) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Sorties.getAll(entityManager));
            if (etat == 20 || etat == 10 || etat == 1) {
                res = mapper.writeValueAsString(Sorties.getAllOfEtat(entityManager, etat));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/sorties/{id}")
    public String getSortieById(@PathParam("id") int id) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Sorties.getById(entityManager, id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/magasins")
    public String getMagasins() {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Magasins.getAll(entityManager));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/magasins/{id}")
    public String getMagasinById(@PathParam("id") int id) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(Magasins.getById(entityManager, id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/etat_stock")
    public String getEtatStock(@QueryParam("date_debut") String dateDebut, @QueryParam("date_fin") String dateFin,
            @QueryParam("id_magasin") int idMagasin) {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper
                    .writeValueAsString(new EtatStock(entityManager, dateDebut, dateFin, idMagasin));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GET
    @Path("/sortie_decomposes")
    public String getSortieDecomposes() {
        ObjectMapper mapper = new ObjectMapper();
        String res = "";
        try {
            res = mapper.writeValueAsString(SortieDecomposes.getAll(entityManager));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

}
