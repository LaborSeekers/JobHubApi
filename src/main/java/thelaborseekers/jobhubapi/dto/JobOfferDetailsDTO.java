package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.JobStatus;

import java.time.LocalDateTime;

@Data

public class JobOfferDetailsDTO {
    private Integer id;

    private String title;

    private String description;

    private String requirements;

    private String location;

    private long salary;

    private String benefits;

    private String logo;

    // En JobOfferDetailsDTO
    private LocalDateTime scheduledPublishAt;

    private String ofertanteName;

    private String jobModalityName;

    private JobStatus status;
}
