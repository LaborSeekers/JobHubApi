package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.Postulacion;

import java.util.Optional;


import java.util.List;

public interface AdminPostulacionService {

    Optional<Postulacion> obtenerPostulacionPorId(Long id);

    String notificarCambioDeEstado(Postulacion postulacion, String nuevoEstado);
    // Nuevo método para obtener historial
    List<Postulacion> obtenerHistorialPorPostulanteId(Integer postulanteId);
}
