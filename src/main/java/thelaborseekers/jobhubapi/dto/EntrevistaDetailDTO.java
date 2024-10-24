package thelaborseekers.jobhubapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EntrevistaDetailDTO {

    private Integer id;

    private LocalDateTime interviewDate;

    private String status;

    private String modality;

    private String interviewPlatform;

    private String interviewLink;

    private String postulanteName; // ID del postulante

    private String ofertanteName; // ID del ofertante
}
