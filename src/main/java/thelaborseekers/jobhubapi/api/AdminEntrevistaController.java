package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.EntrevistaCreateDTO;
import thelaborseekers.jobhubapi.dto.EntrevistaDetailDTO;
import thelaborseekers.jobhubapi.service.AdminEntrevistaService;

import java.util.List;

@RestController
@RequestMapping("admin/entrevista")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
public class AdminEntrevistaController {

    private final AdminEntrevistaService adminEntrevistaService;

    @PostMapping
    public ResponseEntity<EntrevistaDetailDTO> createInterview(@Valid @RequestBody EntrevistaCreateDTO entrevistaCreateDTO) {
        EntrevistaDetailDTO createdInterview = adminEntrevistaService.createInterview(entrevistaCreateDTO);
        return new ResponseEntity<>(createdInterview, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
    @GetMapping("/ofertante/{ofertanteId}")
    public ResponseEntity<List<EntrevistaDetailDTO>> getAllScheduledInterviewsForOfertante(@PathVariable Integer ofertanteId) {
        List<EntrevistaDetailDTO> entrevistas = adminEntrevistaService.getAllScheduledInterviewsForOfertante(ofertanteId);
        return new ResponseEntity<>(entrevistas, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
    @GetMapping("/postulante/{postulanteId}")
    public ResponseEntity<List<EntrevistaDetailDTO>> getAllScheduledInterviewsForPostulante(@PathVariable Integer postulanteId) {
        List<EntrevistaDetailDTO> entrevistas = adminEntrevistaService.getAllScheduledInterviewsForPostulante(postulanteId);
        return new ResponseEntity<>(entrevistas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrevistaDetailDTO> updateInterview(@PathVariable Integer id,
                                                               @Valid @RequestBody EntrevistaCreateDTO entrevistaUpdateDTO) {
        EntrevistaDetailDTO updatedInterview = adminEntrevistaService.updateInterview(id, entrevistaUpdateDTO);
        return new ResponseEntity<>(updatedInterview, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteInterview(@PathVariable Integer id) {
        adminEntrevistaService.deleteInterview(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @GetMapping("/enter/{entrevistaId}")
    public ResponseEntity<String> enterInterview(@PathVariable Integer entrevistaId) {
        String interviewLink = adminEntrevistaService.enterInterview(entrevistaId);
        return ResponseEntity.ok(interviewLink);
    }



}
