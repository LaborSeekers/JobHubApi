package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;
import thelaborseekers.jobhubapi.service.AdminPostulanteService;
import thelaborseekers.jobhubapi.service.ExternalAuthService;
import thelaborseekers.jobhubapi.model.dto.ExternalAccountDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AuthController {

    private final ExternalAuthService externalAuthService;
    private final AdminPostulanteService adminPostulanteService;
    private final AdminOfertanteService adminOfertanteService;

    @PostMapping("/register")
    public ResponseEntity<?> registerExternalAccount(@RequestBody ExternalAccountDto externalAccountDto) {
        try {
            // Llama al servicio para registrar la cuenta externa
            externalAuthService.registerExternalAccount(externalAccountDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cuenta registrada con éxito");
        } catch (Exception e) {
            // Maneja posibles excepciones y errores
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar la cuenta: " + e.getMessage());
        }
    }
    //Postulantes Section
    @GetMapping("/Postulantes")
    public List<Postulante> getAllPostulantes() {
        return adminPostulanteService.findAll();
    }
    @GetMapping("/Postulantes/page")
    public Page<Postulante> paginatePostulantes(@PageableDefault(size = 5, sort = "email") Pageable pageable) {
        return adminPostulanteService.paginate(pageable);
    }

    @PostMapping("/Postulantes")
    @ResponseStatus(HttpStatus.CREATED)
    public Postulante createPostulante(@RequestBody Postulante postulanteForm) {
        return adminPostulanteService.create(postulanteForm);
    }

    @GetMapping("/Postulantes/{id}")
    public Postulante getPostulanteById(@PathVariable Integer id) {
        return adminPostulanteService.findById(id);
    }

    @PutMapping("/Postulantes/{id}")
    public Postulante updatePostulante(@PathVariable Integer id, @RequestBody Postulante postulanteForm) {
        return adminPostulanteService.update(id, postulanteForm);
    }

    @DeleteMapping("/Postulantes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostulante(@PathVariable Integer id) {
        adminPostulanteService.delete(id);
    }

    @GetMapping("/Postulantes/filter")
    public List<Postulante> filterPostulantesByNameAndLastName(@RequestParam String name, @RequestParam String lastName) {
        return adminPostulanteService.filterByNameAndLastName(name, lastName);
    }

    @GetMapping("/Postulantes/filterByAge")
    public List<Postulante> filterPostulantesByAge(@RequestParam int age) {
        return adminPostulanteService.filterByAge(age);
    }

    //Ofertantes Section
    @GetMapping("/Ofertantes")
    public List<Ofertante> GetAllOfertantes() {
        return adminOfertanteService.findAll();
    }

    //paginate//

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/Ofertantes")
    public Ofertante createOfertante(@RequestBody Ofertante ofertanteForm) {
        return adminOfertanteService.create(ofertanteForm);
    }

    @GetMapping("/Ofertantes/{id}")
    public Ofertante getOfertanteById(@PathVariable Integer id) {return adminOfertanteService.findById(id);}

    @PutMapping("/Ofertantes/{id}")
    public Ofertante updateOfertante(@PathVariable Integer id, @RequestBody Ofertante ofertanteForm) {
        return adminOfertanteService.update(id, ofertanteForm);
    }
    @PutMapping("/Ofertantes/{id}/rate")
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
    @DeleteMapping("/Ofertantes/{id}")
    public void deleteOfertante(@PathVariable Integer id) {
        adminOfertanteService.delete(id);
    }




}
