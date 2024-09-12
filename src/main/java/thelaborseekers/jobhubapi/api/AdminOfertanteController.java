package thelaborseekers.jobhubapi.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Ofertantes")
public class AdminOfertanteController {
    private final AdminOfertanteService adminOfertanteService;

    @GetMapping()
    public List<Ofertante> list() {
        return adminOfertanteService.findAll();
    }

    //paginate//


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ofertante create(@RequestBody Ofertante ofertanteForm) {
        return adminOfertanteService.create(ofertanteForm);
    }

    @GetMapping("/{id}")
    public Ofertante get(@PathVariable Integer id) {return adminOfertanteService.findById(id);}

    @PutMapping("/{id}")
    public Ofertante update(@PathVariable Integer id, @RequestBody Ofertante ofertanteForm) {
        return adminOfertanteService.update(id, ofertanteForm);
    }
    @PutMapping("/{id}/rate")
    public ResponseEntity<String> updateReputation(@PathVariable Integer id, @RequestParam Integer ratingValue) {
        if (ratingValue == null) {
            return ResponseEntity.badRequest().body("Rating value is required.");
        }

        // Validar que ratingValue esté en el rango permitido (0 o 1)
        if (ratingValue != 0 && ratingValue != 1) {
            return ResponseEntity.badRequest().body("Rating value must be either 0 or 1.");
        }
        adminOfertanteService.updateReputation(id, ratingValue);
        return ResponseEntity.ok("Reputation updated successfully.");
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminOfertanteService.delete(id);
    }
}
