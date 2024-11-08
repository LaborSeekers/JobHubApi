package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;


    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "logo", columnDefinition = "TEXT")
    private String logo;
}
