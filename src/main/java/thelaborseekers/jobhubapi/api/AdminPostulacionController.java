package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.dto.PostulacionEstadoDTO;
import thelaborseekers.jobhubapi.service.AdminPostulacionService;
import thelaborseekers.jobhubapi.service.impl.AdminPostulacionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin/postulaciones")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
@RequiredArgsConstructor
public class AdminPostulacionController {

    private final AdminPostulacionService adminPostulacionService;
    private final AdminPostulacionServiceImpl adminPostulacionServiceImpl;

    @PostMapping
    public ResponseEntity<PostulacionDTO> crearPostulacion(@Valid @RequestBody PostulacionDTO postulacionDTO) {
        PostulacionDTO respuestaDTO = adminPostulacionService.crearPostulacion(postulacionDTO);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PostulacionDTO> actualizarEstado(@PathVariable("id") Integer id, @RequestBody PostulacionEstadoDTO nuevoEstadoDTO) {
        String nuevoEstado = nuevoEstadoDTO.getNuevoEstado();
        System.out.println("Estado recibido: " + nuevoEstado);

        PostulacionDTO postulacionDTO = adminPostulacionService.actualizarEstado(id, nuevoEstado);

        return new ResponseEntity<>(postulacionDTO, HttpStatus.OK);
    }

    @PutMapping("/notificacion/{id}")
    public ResponseEntity<String> obtenerNotificacion(@PathVariable Integer id) {
        String mensajeNotificacion = adminPostulacionServiceImpl.obtenerNotificacion(id);
        return ResponseEntity.ok(mensajeNotificacion);
    }

    @GetMapping("/oferta/{jobOfferId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OFERTANTE')")
    public ResponseEntity<List<PostulacionDTO>> obtenerPostulacionesPorJobOfferId(@PathVariable Integer jobOfferId) {
        List<PostulacionDTO> postulaciones = adminPostulacionService.obtenerPostulacionesPorJobOfferId(jobOfferId);
        return ResponseEntity.ok(postulaciones);
    }

}
