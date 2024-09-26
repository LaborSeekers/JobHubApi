package thelaborseekers.jobhubapi.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;


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
    private String otp;
    private LocalDateTime otpGeneratedTime;
    private Date birthday;

    @Column(nullable = true)
    private boolean active;

    // Relación uno a muchos con postulaciones
    @OneToMany(mappedBy = "postulante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Para evitar ciclos en la serialización JSON
    private List<Postulacion> postulaciones;
}
