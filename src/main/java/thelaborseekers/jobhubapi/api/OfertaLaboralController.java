package thelaborseekers.jobhubapi.api;

import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.service.OfertaLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/ofertas")
public class OfertaLaboralController {

    @Autowired
    private OfertaLaboralService ofertaLaboralService;

    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrarOfertas(@RequestParam String ubication, @RequestParam String category) {
        try {
            List<JobOffer> ofertas = ofertaLaboralService.filtrarOfertas(ubication, category);
            return ResponseEntity.ok(ofertas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
