package thelaborseekers.jobhubapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.LanguageLevels;

@Data
@Entity
@Table
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Language_Level_id",referencedColumnName = "id")
    private LanguageLevel languageLevel;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "curriculum_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_curriculum_id"))
    private Curriculum curriculum;
}
