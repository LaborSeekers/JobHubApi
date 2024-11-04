package thelaborseekers.jobhubapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.ApplicationStatus;

import java.time.LocalDateTime;

@Data
public class ApplicationDetailDTO {

    private Integer id;

    private Integer postulante_id;

    private Integer jobOffer_id;

    private ApplicationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateApplied;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateUpdated;
}
