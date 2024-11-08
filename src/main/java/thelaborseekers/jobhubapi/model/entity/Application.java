package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.ApplicationStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="Application")
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

    private ApplicationStatus status;

    private LocalDateTime dateApplied;
    private LocalDateTime dateUpdated;
}
