package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.PaymentFrequency;

@Data
public class SubscriptionUpdateDTO {
    @NotBlank(message = "La frecuencia del pago es obligatoria")
    private PaymentFrequency paymentFrequency;
}
