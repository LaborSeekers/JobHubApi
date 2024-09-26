package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.Postulacion;

import java.util.Optional;

public interface AdminPostulacionService {

    Optional<Postulacion> obtenerPostulacionPorId(Long id);

    String notificarCambioDeEstado(Postulacion postulacion, String nuevoEstado);
}
