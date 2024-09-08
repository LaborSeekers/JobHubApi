package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.JobStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "JobOffers")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private String requirements;
    private String logo;
    private String location;

    @Column(name="created_at",nullable=false)
    private LocalDateTime createdAt;

    private long salary;
    private String benefits;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @ManyToOne
    @JoinColumn(name = "jobModality_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_jobModality"))
    private JobModality jobModality;

    @ManyToOne
    @JoinColumn(name = "ofertante_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_ofertante"))
    private Ofertante ofertante;

}
