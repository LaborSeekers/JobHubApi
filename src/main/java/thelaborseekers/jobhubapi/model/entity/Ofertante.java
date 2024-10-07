package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.Reputation;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Ofertante")
public class Ofertante  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastName;
   // private String email;
    //private String password;
    private String phone;
    private Date birthday;

    private Integer reputationValue; //usar enum
    @Enumerated(EnumType.STRING)
    private Reputation reputation;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @PostLoad
    private void computeReputationStatus() {
        this.reputation = Reputation.fromValue(this.reputationValue);
    }
    @ManyToOne
    @JoinColumn(name = "empresa_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_empresa"))
    private Empresa empresa;
}
