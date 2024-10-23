package thelaborseekers.jobhubapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.PostulacionMapper;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.repository.PostulacionRepository;
import thelaborseekers.jobhubapi.service.AdminPostulacionService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminPostulacionServiceImpl implements AdminPostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private PostulacionMapper postulacionMapper;

    @Override
    public Optional<Postulacion> obtenerPostulacionPorId(Long id) {
        return postulacionRepository.findById(id);
    }

    @Override
    public String notificarCambioDeEstado(Postulacion postulacion, String nuevoEstado) {
        nuevoEstado = nuevoEstado.trim(); // Normaliza el estado
        postulacion.setEstado(nuevoEstado);
        postulacionRepository.save(postulacion);

        if (nuevoEstado.equalsIgnoreCase("Aprobado")) {
            return "Felicidades, has pasado a la siguiente etapa en el proceso de selección del puesto";
        } else if (nuevoEstado.equalsIgnoreCase("Finalizado")) {
            return "El proceso de selección para el puesto ha finalizado.";
        } else if (nuevoEstado.equalsIgnoreCase("Cancelado")) {
            return "El proceso de selección para el puesto ha sido cancelado.";
        }
        return "Estado actualizado sin mensaje específico.";
    }

    @Override
    public PostulacionDTO actualizarEstado(Long id, String nuevoEstado) {

        List<String> estadosPermitidos = Arrays.asList("Aprobado", "Finalizado", "Cancelado");

        if(!estadosPermitidos.contains(nuevoEstado)) {
            throw new BadRequestException("Estado inválido. Los valores permitidos son: Aprobado, Finalizado, Cancelado.");
        }

        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación no encontrada para el ID: " + id));

        nuevoEstado = nuevoEstado.trim(); // Elimina espacios en blanco
        System.out.println("Estado que se va a asignar: " + nuevoEstado); // LOG para depuración

        postulacion.setEstado(nuevoEstado); // Asigna el nuevo estado
        postulacionRepository.save(postulacion); // Guarda la postulación

        return postulacionMapper.toDTO(postulacion);
    }


    @Override
    public String obtenerNotificacion(Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación no encontrada para el ID: " + id));

        String estadoActual = postulacion.getEstado();
        return notificarCambioDeEstado(postulacion, estadoActual);
    }

    @Override
    public List<Postulacion> obtenerHistorialPorPostulanteId(Long postulanteId) {
        return postulacionRepository.findByPostulanteId(postulanteId);
    }
}
