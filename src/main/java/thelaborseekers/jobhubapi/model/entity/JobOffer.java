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

    @Column(nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String requirements;

    private String logo;

    private String location;

    @Column(name="created_at",nullable=false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private long salary;

    private String benefits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(name="scheduled_publish_at")
    private LocalDateTime scheduledPublishAt;

    @ManyToOne
    @JoinColumn(name = "jobModality_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_jobModality"))
    private JobModality jobModality;

    @ManyToOne
    @JoinColumn(name = "ofertante_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_ofertante"))
    private Ofertante ofertante;

}
