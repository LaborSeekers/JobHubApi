package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import thelaborseekers.jobhubapi.model.entity.Empresa;
import thelaborseekers.jobhubapi.model.enums.Reputation;

import java.util.Date;
@Data
public class OfertanteProfileDTO {
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50,message = "El nombre debe tener entre 2 a 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50,message = "El apellido debe tener entre 2 a 50 caracteres")
    private String lastName;


    @Size(min = 8, message = "El número telefonico no es valido")
    @NotBlank(message = "El telefono celular es obligatorio")
    private String phone;

    @NotNull(message = "La fecha de cumpleaños es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private Date birthday;

    private Integer reputationValue;
    private Reputation reputation;

    @NotNull(message = "La empresa de procedencia del ofertante es obligatoria")
    private Empresa empresa;
}
