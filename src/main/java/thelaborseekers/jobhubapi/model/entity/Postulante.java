package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import java.util.Date;


@Data
@Entity // crea la tabla
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Postulante")
public class Postulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private boolean active;
    private String otp;
    private LocalDateTime otpGeneratedTime;
    private Date birthday;
}
