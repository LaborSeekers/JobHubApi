package thelaborseekers.jobhubapi.model.dto;


import lombok.Data;

@Data
public class ExternalAccountDto {
    private String externalId;
    private String provider;
    private String email;
    private String name;
}