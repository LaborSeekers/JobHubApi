package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import thelaborseekers.jobhubapi.model.enums.JobStatus;


@Getter
@Setter
public class JobOfferFilterRequestDTO {
    private Integer id;

    @Size(max = 90, message = "El título debe tener como máximo 90 caracteres")
    private String title;

    private String description;

    private String requirements;

    @Size(max = 100, message = "La ubicación debe tener como máximo 100 caracteres")
    private String location;

    private long salary;

    private String benefits;

    private String logo;

    private String ofertanteName;

    private String jobModalityName;

    private JobStatus status;
}
