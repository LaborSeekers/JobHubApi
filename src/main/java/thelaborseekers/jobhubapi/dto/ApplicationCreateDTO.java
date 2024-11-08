package thelaborseekers.jobhubapi.dto;

import lombok.Data;

@Data
public class ApplicationCreateDTO {
    private Integer id;

    private Integer postulante_id;

    private Integer jobOffer_id;
}
