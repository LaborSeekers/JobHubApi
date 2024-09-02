package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Users")
//public abstract class User //de momento solo funciona la agregacion de usuario sin especificar el tipo de usuario (obj)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "Type_user_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_Type_user"))
    private Type_user Type_user;
}
