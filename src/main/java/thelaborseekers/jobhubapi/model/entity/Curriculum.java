package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Curriculums")
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "postulante_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_postulante"))
    private Postulante postulante;

    private String content; // Se podría almacenar el contenido en formato JSON o texto plano


}
