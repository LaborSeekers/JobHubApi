package thelaborseekers.jobhubapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.EduLevel;

import java.time.LocalDate;

@Data
@Entity
@Table
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "institution_Name", nullable = false)
    private String institutionName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Education_Level_id",referencedColumnName = "id")
    private EducationLevel eduLevel;

    @Column(name = "start_Date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_Date")
    private LocalDate endDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "curriculum_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_curriculum_id"))
    Curriculum curriculum;
}
