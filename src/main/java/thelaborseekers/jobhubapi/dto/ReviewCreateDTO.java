package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewCreateDTO {

    private Integer id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no debe exceder los 100 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String description;

    @NotNull(message = "La cantidad de estrellas es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1 estrella")
    @Max(value = 5, message = "La calificación máxima es 5 estrellas")
    private Integer stars;

    @NotNull(message = "El ID del postulante es obligatorio")
    private Integer postulante_id;

    @NotNull(message = "El ID de la oferta de trabajo es obligatorio")
    private Integer jobOffer_id;

}
