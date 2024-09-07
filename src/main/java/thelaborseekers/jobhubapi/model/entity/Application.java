package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "postulante_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_postulante"))
    private Postulante postulante;

    @ManyToOne
    @JoinColumn(name = "joboffer_id")
    private JobOffer jobOffer;

    private Date dateApplied;
}
