package thelaborseekers.jobhubapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostulacionDTO {
    private Long id;
    private String postulanteNombre;
    private String ofertaLaboralTitulo;
    private String estado;
    private LocalDate fechaAplicacion;
}
