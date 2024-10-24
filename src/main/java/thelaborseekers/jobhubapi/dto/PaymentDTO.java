package thelaborseekers.jobhubapi.dto;

import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.PaymentType;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {

    private Integer id;
    private String ofertante;
    private PaymentType type;
    private LocalDateTime paymentDate;
    private Float amount;
    private Integer quantity;
    private Float total;
    private String method;
    private String description;
}
