package thelaborseekers.jobhubapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.repository.PostulacionRepository;
import thelaborseekers.jobhubapi.service.AdminPostulacionService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminPostulacionServiceImpl implements AdminPostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Override
    public Optional<Postulacion> obtenerPostulacionPorId(Long id) {
        return postulacionRepository.findById(id);
    }

    @Override
    public String notificarCambioDeEstado(Postulacion postulacion, String nuevoEstado) {
        // Normaliza el estado a mayúsculas para evitar problemas de coincidencia
        nuevoEstado = nuevoEstado.trim(); // Eliminar espacios en blanco
        postulacion.setEstado(nuevoEstado);
        postulacionRepository.save(postulacion);

        if (nuevoEstado.equalsIgnoreCase("Aprobado")) { // Compara sin importar mayúsculas
            return "Felicidades, has pasado a la siguiente etapa en el proceso de selección del puesto de ABC de la empresa XYZ.";
        } else if (nuevoEstado.equalsIgnoreCase("Finalizado")) {
            return "El proceso de selección para el puesto ABC de la empresa XYZ ha finalizado.";
        } else if (nuevoEstado.equalsIgnoreCase("Cancelado")) {
            return "El proceso de selección para el puesto ABC de la empresa XYZ ha sido cancelado.";
        }
        return "Estado actualizado sin mensaje específico."; // Mensaje por defecto si el estado no coincide
    }


}
