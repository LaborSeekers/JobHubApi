package thelaborseekers.jobhubapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.service.AdminPostulacionService;
import thelaborseekers.jobhubapi.service.impl.AdminPostulacionServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/postulaciones")
public class AdminPostulacionController {

    @Autowired
    private AdminPostulacionService adminPostulacionService;
    @Autowired
    private AdminPostulacionServiceImpl adminPostulacionServiceImpl;

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado){
        Optional<Postulacion> postulacionOpt = adminPostulacionService.obtenerPostulacionPorId(id);
        if (postulacionOpt.isPresent()) {
            Postulacion postulacion = postulacionOpt.get();
            adminPostulacionService.notificarCambioDeEstado(postulacion, nuevoEstado);
            return ResponseEntity.ok("Estado actualizado y notificacion enviada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postulación no encontrada");
        }
    }

    @PutMapping("/notificacion/{id}")
    public ResponseEntity<String> obtenerNotificacion(@PathVariable Long id) {
        Optional<Postulacion> postulacionOpt = adminPostulacionService.obtenerPostulacionPorId(id);

        if (postulacionOpt.isPresent()) {
            Postulacion postulacion = postulacionOpt.get();
            String estadoActual = postulacion.getEstado(); // Asumiendo que el estado se guarda en la entidad
            String mensajeNotificacion = adminPostulacionService.notificarCambioDeEstado(postulacion, estadoActual);
            return ResponseEntity.ok(mensajeNotificacion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postulación no encontrada");
        }
    }
    @GetMapping("/historial/{postulanteId}")
    public ResponseEntity<List<Postulacion>> obtenerHistorialDePostulaciones(@PathVariable Long postulanteId) {
        List<Postulacion> historial = adminPostulacionService.obtenerHistorialPorPostulanteId(postulanteId);
        
        if (historial.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(historial);
    }


}
