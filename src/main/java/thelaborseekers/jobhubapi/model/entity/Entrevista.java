package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Entrevista")
public class Entrevista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "interview_date", nullable = false)
    private LocalDateTime interviewDate;

    private String status;

    private String modality;

    private String interviewPlatform;

    private String interviewLink;

    @ManyToOne
    @JoinColumn(name = "postulante_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_entrevista_postulante"))
    private Postulante postulante;

    @ManyToOne
    @JoinColumn(name = "ofertante_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_entrevista_ofertante"))
    private Ofertante ofertante;
}
