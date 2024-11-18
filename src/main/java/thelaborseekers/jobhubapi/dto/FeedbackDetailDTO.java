package thelaborseekers.jobhubapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDetailDTO {

    private Integer jobOfferID;

    private String jobTitle;
    private String jobLocation;
    private long jobSalary;
    private String jobLogo;
    private String jobModality;

    private Integer applicationID;

    private LocalDateTime dateCreated;

    private String content;
}
