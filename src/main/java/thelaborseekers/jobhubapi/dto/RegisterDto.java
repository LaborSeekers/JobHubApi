package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50,message = "El nombre debe tener entre 2 a 50 caracteres")
    private String name;
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;
    private String password;

}
