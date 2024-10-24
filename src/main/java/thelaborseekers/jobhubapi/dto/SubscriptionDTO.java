package thelaborseekers.jobhubapi.dto;

import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.PaymentFrequency;
import thelaborseekers.jobhubapi.model.enums.SubscriptionStatus;

import java.time.LocalDate;

@Data
public class SubscriptionDTO {

    private Integer id;
    private String ofertante;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createDate;
    private LocalDate updateDate;
    private SubscriptionStatus status;
    private PaymentFrequency paymentFrequency;
    private Float monthlyRate;
    private Float totalAmount;
}
