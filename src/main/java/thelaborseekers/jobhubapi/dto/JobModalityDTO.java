package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobModalityDTO {
    private Integer id;

    @NotBlank(message = "El nombre de la modalidad de trabajo es obligatorio")
    @Size(min = 2, max = 30,message = "El nombre de la modalidad de trabajo debe tener entre 2 a 20 caracteres")
    private String name;
    @Size(min = 2,message = "El nombre de la modalidad de trabajo debe tener entre 2 a mas caracteres")
    @NotBlank(message = "La descripcion de la modalidad de trabajo es obligatoria")
    private String description;
}
