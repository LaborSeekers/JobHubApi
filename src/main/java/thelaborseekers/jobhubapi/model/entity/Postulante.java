package thelaborseekers.jobhubapi.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    private String firstName;
    private String lastName;
    //private String email;
    //private String password;
    private String phone;
    private String otp;
    private LocalDateTime otpGeneratedTime;
    private Date birthday;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;

    // Relación uno a muchos con postulaciones
    @OneToMany(mappedBy = "postulante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Para evitar ciclos en la serialización JSON
    private List<Postulacion> postulaciones;

}
