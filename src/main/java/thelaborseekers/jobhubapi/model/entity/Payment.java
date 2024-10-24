package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.PaymentType;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ofertante_id",referencedColumnName = "id")
    private Ofertante ofertante;

    @Column(name = "type")
    private PaymentType type;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "Total")
    private Float total;

    @Column(name = "method")
    private String method;

    @Column(name = "description")
    private String description;
}
