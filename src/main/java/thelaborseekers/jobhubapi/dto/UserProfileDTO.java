package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import thelaborseekers.jobhubapi.model.entity.Empresa;

import java.util.Date;

@Data
public class UserProfileDTO {
    private Integer id;
    @Email(message = "El correo electronico no es válido")
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;
    private String role;
    private Integer userRoleId;
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    private String phone;

    @NotNull(message = "La fecha de cumpleaños es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private Date birthday;

    private Empresa empresa;
}
