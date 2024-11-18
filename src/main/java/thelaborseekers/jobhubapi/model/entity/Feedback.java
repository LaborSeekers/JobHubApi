package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Feedback")
@IdClass(FeedbackPK.class)
public class Feedback {
    @Id
    @ManyToOne
    @JoinColumn(name = "JobOffer_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_feedback_joboffer"))
    private JobOffer jobOffer;
    @Id
    @ManyToOne
    @JoinColumn(name = "Application_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_feedback_application"))
    private Application application;

    @Column(name = "Contenido")
    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}
