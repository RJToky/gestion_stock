package mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Query;

@Entity
public class Validations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_mouvement")
    private Mouvements mouvement;

    @JsonProperty("date_validation")
    @Column(name = "date_validation")
    private String dateValidation;

    public Validations() {
    }

    public Validations(int idMouvement, String dateValidation) {
        this.setMouvement(new Mouvements(idMouvement));
        this.setDateValidation(dateValidation);
    }

    public void insert(EntityManager entityManager) {
        String sql = "insert into validations values (default, :id_mouvement, cast(:date_validation as date))";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id_mouvement", this.getMouvement().getId());
        query.setParameter("date_validation", this.getDateValidation());
        query.executeUpdate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mouvements getMouvement() {
        return mouvement;
    }

    public void setMouvement(Mouvements mouvement) {
        this.mouvement = mouvement;
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(String dateValidation) {
        this.dateValidation = dateValidation;
    }

}
