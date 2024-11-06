package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.model.entity.Postulacion;

import java.util.Optional;


import java.util.List;

public interface AdminPostulacionService {

    Optional<Postulacion> obtenerPostulacionPorId(Long id);

    String notificarCambioDeEstado(Postulacion postulacion, String nuevoEstado);

    PostulacionDTO actualizarEstado(Long id, String nuevoEstado);

    String obtenerNotificacion(Long id);
    // Nuevo método para obtener historial
    List<Postulacion> obtenerHistorialPorPostulanteId(Integer postulanteId);

    PostulacionDTO crearPostulacion(PostulacionDTO postulacionDTO);
    List<PostulacionDTO> obtenerPostulacionesPorJobOfferId(Long jobOfferId);
}
