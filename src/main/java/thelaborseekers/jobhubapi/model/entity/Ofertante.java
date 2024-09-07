package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Ofertante")
public class Ofertante  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Date birthday;

    private Integer reputationValue; //usar enum

    @OneToOne
    @JoinColumn(name = "empresa_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_empresa"))
    private Empresa empresa;
}
