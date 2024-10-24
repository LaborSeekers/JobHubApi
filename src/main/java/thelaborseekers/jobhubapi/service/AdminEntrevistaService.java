package thelaborseekers.jobhubapi.service;
import thelaborseekers.jobhubapi.dto.EntrevistaCreateDTO;
import thelaborseekers.jobhubapi.dto.EntrevistaDetailDTO;

import java.util.List;
public interface AdminEntrevistaService {
    // CRUD

    // Crear (programar) una entrevista
    EntrevistaDetailDTO createInterview(EntrevistaCreateDTO entrevistaCreateDTO);

    // Obtener todas las entrevistas programadas por ofertante
    List<EntrevistaDetailDTO> getAllScheduledInterviewsForOfertante(Integer ofertanteId);

    // Obtener todas las entrevistas programadas por postulante
    List<EntrevistaDetailDTO> getAllScheduledInterviewsForPostulante(Integer postulanteId);

    // Modificar una entrevista
    EntrevistaDetailDTO updateInterview(Integer entrevistaId, EntrevistaCreateDTO entrevistaUpdateDTO);

    // Entrar a una entrevista
    String enterInterview(Integer entrevistaId);

    void deleteInterview(Integer entrevistaId);
}
