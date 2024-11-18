package thelaborseekers.jobhubapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.JobStatus;
import thelaborseekers.jobhubapi.model.enums.Reputation;

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

    private Reputation reputation;

    // En JobOfferDetailsDTO

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduledPublishAt;

    private String ofertanteName;

    private String jobModalityName;

    private JobStatus status;
}
