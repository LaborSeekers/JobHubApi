package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaDTO {
    private Integer id;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 2, max = 50,message = "El nombre de la empresa debe tener entre 2 a 50 caracteres")
    private String name;

    @NotBlank(message = "La descripcion de la empresa es obligatoria")
    @Size(min = 2,message = "La descripcion de la empresa debe tener entre 2 a mas caracteres")
    private String description;
    private String logo;
}
