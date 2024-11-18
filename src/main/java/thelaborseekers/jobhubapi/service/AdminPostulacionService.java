package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.model.entity.Postulacion;

import java.util.Optional;


import java.util.List;

public interface AdminPostulacionService {

    Optional<Postulacion> obtenerPostulacionPorId(Integer id);

    String notificarCambioDeEstado(Postulacion postulacion, String nuevoEstado);

    PostulacionDTO actualizarEstado(Integer id, String nuevoEstado);

    String obtenerNotificacion(Integer id);
    // Nuevo método para obtener historial
    List<Postulacion> obtenerHistorialPorPostulanteId(Integer postulanteId);

    PostulacionDTO crearPostulacion(PostulacionDTO postulacionDTO);
    List<PostulacionDTO> obtenerPostulacionesPorJobOfferId(Integer jobOfferId);
}
