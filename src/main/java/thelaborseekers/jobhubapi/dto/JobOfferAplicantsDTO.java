package thelaborseekers.jobhubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobOfferAplicantsDTO {
    private String jobTitle;
    private long applicantsCount;
}
