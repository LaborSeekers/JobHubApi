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
    // Nuevos campos de ID para postulante y oferta laboral
    private Long postulanteId;
    private Long ofertaLaboralId;
}
