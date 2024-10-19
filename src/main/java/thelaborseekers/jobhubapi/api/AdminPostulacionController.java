package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.dto.PostulacionEstadoDTO;
import thelaborseekers.jobhubapi.mapper.PostulacionMapper;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.service.AdminPostulacionService;
import thelaborseekers.jobhubapi.service.impl.AdminPostulacionServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/postulaciones")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
public class AdminPostulacionController {

    @Autowired
    private AdminPostulacionService adminPostulacionService;

    @Autowired
    private PostulacionMapper postulacionMapper;
    @Autowired
    private AdminPostulacionServiceImpl adminPostulacionServiceImpl;

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(@PathVariable Long id, @Valid @RequestBody PostulacionEstadoDTO nuevoEstadoDTO) {
        String nuevoEstado = nuevoEstadoDTO.getNuevoEstado();
        System.out.println("Estado recibido: " + nuevoEstado); // LOG para verificar que el estado se recibe correctamente
        PostulacionDTO postulacionDTO = adminPostulacionService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok("Estado actualizado y notificación enviada: " + postulacionDTO.getEstado());
    }

    @PutMapping("/notificacion/{id}")
    public ResponseEntity<String> obtenerNotificacion(@PathVariable Long id) {
        String mensajeNotificacion = adminPostulacionServiceImpl.obtenerNotificacion(id);
        return ResponseEntity.ok(mensajeNotificacion);
    }
}
