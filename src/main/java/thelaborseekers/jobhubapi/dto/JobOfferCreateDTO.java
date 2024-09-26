package thelaborseekers.jobhubapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.JobStatus;

import java.time.LocalDateTime;
@Data
public class JobOfferCreateDTO {

    private Integer id;



    @NotBlank(message = "El título es obligatorio")
    @Size(max = 90, message = "El título debe tener como máximo 90 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "Los requisitos son obligatorios")
    @Size(max = 255, message = "Los requisitos deben tener como máximo 255 caracteres")
    private String requirements;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 100, message = "La ubicación debe tener como máximo 100 caracteres")
    private String location;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser un valor positivo")
    @Min(value = 100, message = "El salario debe ser correspondiente")
    private long salary;

    @Size(max = 255, message = "Los beneficios deben tener como máximo 255 caracteres")
    private String benefits;

    private String logo;

    @Column(name="scheduled_publish_at")
    private LocalDateTime scheduledPublishAt;

    @NotNull(message = "La modalidad es obligatoria")
    private Integer jobModality_id;

    @NotNull(message = "El ofertante es obligatorio")
    private Integer ofertante_id;

    private JobStatus status;
}
