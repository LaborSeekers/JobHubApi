package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.OfertanteProfileDTO;
import thelaborseekers.jobhubapi.dto.OfertanteRegisterDTO;
import thelaborseekers.jobhubapi.dto.PostulanteRegisterDTO;
import thelaborseekers.jobhubapi.dto.PostulanteProfileDTO;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
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
    public List<PostulanteProfileDTO> getAllPostulantes() {
        return adminPostulanteService.findAll();
    }
    @GetMapping("/Postulantes/page")
    public Page<PostulanteRegisterDTO> paginatePostulantes(@PageableDefault(size = 5, sort = "email") Pageable pageable) {
        return adminPostulanteService.paginate(pageable);
    }

    @PostMapping("/Postulantes")
    @ResponseStatus(HttpStatus.CREATED)
    public PostulanteRegisterDTO createPostulante(@Valid @RequestBody PostulanteRegisterDTO postulanteForm) {
        return adminPostulanteService.create(postulanteForm);
    }

    @GetMapping("/Postulantes/{id}")
    public PostulanteProfileDTO getPostulanteById(@PathVariable Integer id) {
        return adminPostulanteService.findById(id);
    }

    @PutMapping("/Postulantes/{id}")
    public PostulanteRegisterDTO updatePostulante(@PathVariable Integer id, @Valid @RequestBody PostulanteRegisterDTO postulanteForm) {
        return adminPostulanteService.update(id, postulanteForm);
    }

    @DeleteMapping("/Postulantes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostulante(@PathVariable Integer id) {
        adminPostulanteService.delete(id);
    }

    @GetMapping("/Postulantes/filter/{name}/{lastName}")
    public List<PostulanteProfileDTO> filterPostulantesByNameAndLastName(@PathVariable String name, @PathVariable String lastName) {
        return adminPostulanteService.filterByNameAndLastName(name, lastName);
    }

    @GetMapping("/Postulantes/filterByAge")
    public List<PostulanteProfileDTO> filterPostulantesByAge(@RequestParam int age) {
        return adminPostulanteService.filterByAge(age);
    }

    //Ofertantes Section
    @GetMapping("/Ofertantes")
    public List<OfertanteProfileDTO> GetAllOfertantes() {
        return adminOfertanteService.findAll();
    }

    //paginate//

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/Ofertantes")
    public OfertanteRegisterDTO createOfertante(@Valid @RequestBody OfertanteRegisterDTO ofertanteForm) {
        return adminOfertanteService.create(ofertanteForm);
    }

    @GetMapping("/Ofertantes/{id}")
    public OfertanteProfileDTO getOfertanteById(@PathVariable Integer id) {return adminOfertanteService.findById(id);}

    @PutMapping("/Ofertantes/{id}")
    public OfertanteRegisterDTO updateOfertante(@PathVariable Integer id, @Valid @RequestBody OfertanteRegisterDTO ofertanteForm) {
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
