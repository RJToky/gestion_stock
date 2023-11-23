package mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

@Entity
@Table(name = "sortie_decomposes")
public class SortieDecomposes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JsonProperty("sortie")
    @JoinColumn(name = "id_mouvement_sortie")
    private Sorties sortie;

    @OneToOne
    @JsonProperty("entree")
    @JoinColumn(name = "id_mouvement_entree")
    private Entrees entree;

    private double quantite;

    public SortieDecomposes() {
    }

    public SortieDecomposes(Sorties sortie, Entrees entree, double quantite) {
        this.sortie = sortie;
        this.entree = entree;
        this.quantite = quantite;
    }

    public SortieDecomposes(Sorties sortie) {
        this.setSortie(sortie);
    }

    public void decomposer(EntityManager entityManager) {
        String methode = this.getSortie().getArticle().getMethode().getNom();

        if (methode.equalsIgnoreCase("fifo")) {
            this.fifo(entityManager);
        } else if (methode.equalsIgnoreCase("lifo")) {
            this.lifo(entityManager);
        }
    }

    public void fifo(EntityManager entityManager) {
        List<Entrees> entrees = Entrees.getByIdMagasinAndIdArticle(entityManager, this.getSortie().getMagasin().getId(),
                this.getSortie().getArticle().getId());

        double quantiteSortie = this.getSortie().getQuantite();
        for (int i = 0; quantiteSortie != 0; i++) {
            double quantiteRestante = entrees.get(i).calculQuantiteRestanteByEntree(entityManager,
                    this.getSortie().getDateMouvement());

            if (quantiteSortie > quantiteRestante && quantiteRestante != 0) {
                SortieDecomposes sortieDecompose = new SortieDecomposes(this.getSortie(), entrees.get(i),
                        quantiteRestante);
                sortieDecompose.insert(entityManager);
                quantiteSortie -= quantiteRestante;

            } else if (quantiteSortie <= quantiteRestante) {
                SortieDecomposes sortieDecompose = new SortieDecomposes(this.getSortie(), entrees.get(i),
                        quantiteSortie);
                sortieDecompose.insert(entityManager);
                quantiteSortie -= quantiteSortie;
            }
        }
    }

    public void lifo(EntityManager entityManager) {
        List<Entrees> entrees = Entrees.getByIdMagasinAndIdArticle(entityManager, this.getSortie().getMagasin().getId(),
                this.getSortie().getArticle().getId());

        double quantiteSortie = this.getSortie().getQuantite();
        for (int i = entrees.size() - 1; quantiteSortie != 0; i--) {
            double quantiteRestante = entrees.get(i).calculQuantiteRestanteByEntree(entityManager,
                    this.getSortie().getDateMouvement());

            if (quantiteSortie > quantiteRestante && quantiteRestante != 0) {
                SortieDecomposes sortieDecompose = new SortieDecomposes(this.getSortie(), entrees.get(i),
                        quantiteRestante);
                sortieDecompose.insert(entityManager);
                quantiteSortie -= quantiteRestante;

            } else if (quantiteSortie <= quantiteRestante) {
                SortieDecomposes sortieDecompose = new SortieDecomposes(this.getSortie(), entrees.get(i),
                        quantiteSortie);
                sortieDecompose.insert(entityManager);
                quantiteSortie -= quantiteSortie;
            }
        }
    }

    public void insert(EntityManager entityManager) {
        String req = "insert into sortie_decomposes values (default, :id_mouvement_sortie, :id_mouvement_entree, :quantite)";
        Query query = entityManager.createNativeQuery(req);
        query.setParameter("id_mouvement_sortie", this.getSortie().getId());
        query.setParameter("id_mouvement_entree", this.getEntree().getId());
        query.setParameter("quantite", this.getQuantite());
        query.executeUpdate();
    }

    public static List<SortieDecomposes> getAll(EntityManager entityManager) {
        String sql = "select * from sortie_decomposes";
        Query query = entityManager.createNativeQuery(sql, SortieDecomposes.class);
        return query.getResultList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public Sorties getSortie() {
        return sortie;
    }

    public void setSortie(Sorties sortie) {
        this.sortie = sortie;
    }

    public Entrees getEntree() {
        return entree;
    }

    public void setEntree(Entrees entree) {
        this.entree = entree;
    }

}
