package thelaborseekers.jobhubapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "position_Name", nullable = false)
    private String positionName;
    @Column(name = "company_Name", nullable = false)
    private String companyName;

    private String description;

    @Column(name = "start_Date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_Date")
    private LocalDate endDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "curriculum_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_curriculum_id"))
    private Curriculum curriculum;
}
