package thelaborseekers.jobhubapi.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String Name;
    private String lastName;
    private String Role;
}
