package thelaborseekers.jobhubapi.dto;

import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.JobStatus;

@Data
public class ReviewDetailsDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer stars;
    private String postulanteName;
    private String jobOfferTitle;

}
