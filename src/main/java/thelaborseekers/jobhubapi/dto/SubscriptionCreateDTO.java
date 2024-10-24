package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.PaymentFrequency;

@Data
public class SubscriptionCreateDTO {
    @NotNull(message = "El id del usuario es obligatorio")
    private Integer ofertante;
    @NotBlank(message = "La frecuencia del pago es obligatoria")
    private PaymentFrequency paymentFrequency;
}
