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
@RequestMapping("/admin/postulaciones")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
public class AdminPostulacionController {

    @Autowired
    private AdminPostulacionService adminPostulacionService;

    @Autowired
    private PostulacionMapper postulacionMapper;
    @Autowired
    private AdminPostulacionServiceImpl adminPostulacionServiceImpl;

    @PostMapping
    public ResponseEntity<PostulacionDTO> crearPostulacion(@Valid @RequestBody PostulacionDTO postulacionDTO) {
        PostulacionDTO respuestaDTO = adminPostulacionService.crearPostulacion(postulacionDTO);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PostulacionDTO> actualizarEstado(@PathVariable("id") Long id, @RequestBody PostulacionEstadoDTO nuevoEstadoDTO) {
        String nuevoEstado = nuevoEstadoDTO.getNuevoEstado();
        System.out.println("Estado recibido: " + nuevoEstado);

        PostulacionDTO postulacionDTO = adminPostulacionService.actualizarEstado(id, nuevoEstado);

        return new ResponseEntity<>(postulacionDTO, HttpStatus.OK);
    }

    @PutMapping("/notificacion/{id}")
    public ResponseEntity<String> obtenerNotificacion(@PathVariable Long id) {
        String mensajeNotificacion = adminPostulacionServiceImpl.obtenerNotificacion(id);
        return ResponseEntity.ok(mensajeNotificacion);
    }

    @GetMapping("/oferta/{jobOfferId}")
@PreAuthorize("hasRole('ADMIN') or hasRole('OFERTANTE')")
public ResponseEntity<List<PostulacionDTO>> obtenerPostulacionesPorJobOfferId(@PathVariable Long jobOfferId) {
    List<PostulacionDTO> postulaciones = adminPostulacionService.obtenerPostulacionesPorJobOfferId(jobOfferId);
    return ResponseEntity.ok(postulaciones);
}

}
