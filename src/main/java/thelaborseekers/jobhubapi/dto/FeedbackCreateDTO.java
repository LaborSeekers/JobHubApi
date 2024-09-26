package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackCreateDTO {
    @NotNull(message = "La oferta laboral es obligatoria")
    private Integer jobOfferID;

    @NotNull(message = "La aplicacion es obligatoria")
    private Integer applicationID;

    @NotBlank(message = "El contenido del feedback es obligatorio")
    @Size(min = 5, max = 500,message = "El contenido debe tener entre 5 a 500 caracteres")
    private String content;
}
