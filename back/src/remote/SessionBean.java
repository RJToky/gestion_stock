package remote;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mapping.Entrees;
import mapping.Sorties;

@Stateless
public class SessionBean implements RemoteStock {
    @PersistenceContext(name = "persistenceUnitStock")
    private EntityManager entityManager;

    public void insertEntree(int idMagasin, int idArticle, double quantite, double prixUnitaire, String date) {
        Entrees entree = new Entrees(idMagasin, idArticle, quantite, prixUnitaire, date);
        entree.insert(entityManager);
    }

    public void saisirSortie(int idMagasin, int idArticle, double quantite, String date)
            throws Exception {
        Sorties sortie = new Sorties(idMagasin, idArticle, quantite, date);
        sortie.saisir(entityManager);
    }

    public void validerSortie(int id, String dateValidation, double quantite) throws Exception {
        Sorties sortie = Sorties.getById(entityManager, id);
        sortie.setDateValidation(dateValidation);
        sortie.valider(entityManager, quantite);
    }

    public void annulerSortie(int id) throws Exception {
        Sorties sortie = new Sorties(id);
        sortie.annuler(entityManager);
    }
}
