package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.entity.Empresa;

import java.util.Date;

@Data
public class UserRegistrationDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    @Email(message = "El correo electronico no es válido")
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;
    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 7,message = "La contraseña debe tener al menos 7 caracteres")
    private String password;

    private String phone;
    @NotNull(message = "La fecha de cumpleaños es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private Date birthday;


    private Empresa empresa;
}
