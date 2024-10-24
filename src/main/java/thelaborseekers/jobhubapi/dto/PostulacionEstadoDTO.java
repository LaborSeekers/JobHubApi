package thelaborseekers.jobhubapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostulacionEstadoDTO {
    @NotNull(message = "El nuevo estado no puede ser nulo")
    @Pattern(regexp = "Aprobado|Finalizado|Cancelado", message = "Estado inválido. Los valores permitidos son: Aprobado, Finalizado, Cancelado")
    private String nuevoEstado;
}
