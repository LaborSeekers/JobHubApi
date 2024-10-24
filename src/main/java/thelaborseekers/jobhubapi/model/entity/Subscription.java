package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.PaymentFrequency;
import thelaborseekers.jobhubapi.model.enums.SubscriptionStatus;

import java.time.LocalDate;

@Data
@Entity
@Table(name="subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ofertante_id",referencedColumnName = "id")
    private Ofertante ofertante;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_time")
    private LocalDate endDate;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @Column(name="payment_frequence", nullable = false)
    private PaymentFrequency paymentFrequency;

    @Column(name="monthly_rate", nullable = false)
    private Float monthlyRate;

    @Column(name="total_amount", nullable = false)
    private Float TotalAmount;
}
