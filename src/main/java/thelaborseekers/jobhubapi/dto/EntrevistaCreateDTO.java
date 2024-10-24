package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntrevistaCreateDTO {

    private Integer id;

    @NotNull(message = "La fecha de la entrevista es obligatoria.")
    private LocalDateTime interviewDate;

    @NotBlank(message = "El estado es obligatorio.")
    @Size(max = 150, message = "El estado debe tener como máximo 150 caracteres.")
    private String status;

    @NotBlank(message = "La modalidad es obligatoria.")
    @Size(max = 150, message = "La modalidad debe tener como máximo 150 caracteres.")
    private String modality;

    @NotBlank(message = "La plataforma de la entrevista es obligatoria.")
    @Size(max = 150, message = "La plataforma de la entrevista debe tener como máximo 150 caracteres.")
    private String interviewPlatform;

    @NotBlank(message = "El enlace de la entrevista es obligatorio.")
    @Size(max = 250, message = "El enlace de la entrevista debe tener como máximo 250 caracteres.")
    private String interviewLink;

    @NotNull(message = "El ID del postulante es obligatorio.")
    private Integer postulanteId; // ID del postulante

    @NotNull(message = "El ID del ofertante es obligatorio.")
    private Integer ofertanteId; // ID del ofertante
}
