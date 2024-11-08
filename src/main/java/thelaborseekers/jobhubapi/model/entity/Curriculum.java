package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Curriculum")
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "postulante_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_postulante"))
    private Postulante postulante;

    private String content; // Se podría almacenar el contenido en formato JSON o texto plano

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages; // Lista de idiomas que habla el postulante.

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> work_experience; // Lista de experiencias laborales del postulante.

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education;
}
