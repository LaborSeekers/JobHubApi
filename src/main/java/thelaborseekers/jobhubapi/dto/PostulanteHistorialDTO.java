package thelaborseekers.jobhubapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostulanteHistorialDTO {
    private int cantidadPostulaciones;
    private List<PostulacionDTO> postulaciones;
}
