package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteJobOfferCreateDTO {

    @NotNull(message = "El postulante es obligatorio")
    private Integer postulanteID;

    @NotNull(message = "La oferta laboral es obligatoria")
    private Integer jobOfferID;

}
