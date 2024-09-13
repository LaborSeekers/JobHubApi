package thelaborseekers.jobhubapi.model.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private Integer stars;



    @ManyToOne
    @JoinColumn(name = "Postulante_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_postulante"))
    private Postulante postulante;

    @ManyToOne
    @JoinColumn(name = "JobOffer_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_jobOffer"))
    private JobOffer jobOffer;
}
