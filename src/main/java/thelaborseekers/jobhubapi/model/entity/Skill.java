package thelaborseekers.jobhubapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name = "skill_Name")
    private String skillName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "curriculum_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_curriculum_id" ))
    private Curriculum curriculum;
}
