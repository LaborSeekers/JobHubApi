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
    @JoinColumn(name = "postulante_id")
    private Postulante postulante;


    @ManyToOne
    @JoinColumn(name = "joboffer_id")
    private JobOffer jobOffer;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    private Date dateApplied;
}
