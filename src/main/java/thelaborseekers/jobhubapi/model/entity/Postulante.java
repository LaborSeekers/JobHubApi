package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity // crea la tabla
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Postulante")
//public abstract class User //de momento solo funciona la agregacion de usuario sin especificar el tipo de usuario (obj)
public class Postulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Date birthday;

}
