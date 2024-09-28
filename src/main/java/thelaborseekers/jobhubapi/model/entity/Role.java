package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.Erole;

@Data
@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name",nullable = false /*,unique = true*/)
    private Erole name;

}
