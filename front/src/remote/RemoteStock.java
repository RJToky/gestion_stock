package remote;

import jakarta.ejb.Remote;

@Remote
public interface RemoteStock {

        void insertEntree(int idMagasin, int idArticle, double quantite, double prixUnitaire, String date);

        void saisirSortie(int idMagasin, int idArticle, double quantite, String date) throws Exception;

        void validerSortie(int id, String dateValidation, double quantite) throws Exception;

        void annulerSortie(int id) throws Exception;
}