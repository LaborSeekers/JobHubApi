package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.*;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;
import thelaborseekers.jobhubapi.service.AdminPostulanteService;
import thelaborseekers.jobhubapi.service.ExternalAuthService;
import thelaborseekers.jobhubapi.model.dto.ExternalAccountDto;
import thelaborseekers.jobhubapi.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final ExternalAuthService externalAuthService;
    private final AdminPostulanteService adminPostulanteService;
    private final AdminOfertanteService adminOfertanteService;

    @PostMapping("/register/Postulantes")
    public ResponseEntity<UserProfileDTO> registerPostulante(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfile = userService.registerPostulante(userRegistrationDTO);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }
        
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/register/Ofertantes")
    public ResponseEntity<UserProfileDTO> registerOfertante(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfile = userService.registerOfertante(userRegistrationDTO);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }



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
    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
    public PostulanteRegisterDTO updatePostulante(@PathVariable Integer id, @Valid @RequestBody PostulanteRegisterDTO postulanteForm) {
        return adminPostulanteService.update(id, postulanteForm);
    }


    @DeleteMapping("/Postulantes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
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

    @GetMapping("/Postulantes/{postulanteId}/historial")
    public ResponseEntity<?> getHistorialPostulaciones(@PathVariable Integer postulanteId) {
        // Obtener historial de postulaciones del postulante
        PostulanteHistorialDTO historial = adminPostulanteService.getHistorialPostulaciones(postulanteId);
        
        return ResponseEntity.ok(historial);
    }



    //Ofertantes Section
    @GetMapping("/Ofertantes")
    public List<OfertanteProfileDTO> GetAllOfertantes() {
        return adminOfertanteService.findAll();
    }

    //paginate//

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/Ofertantes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OfertanteRegisterDTO createOfertante(@Valid @RequestBody OfertanteRegisterDTO ofertanteForm) {
        return adminOfertanteService.create(ofertanteForm);
    }

    @GetMapping("/Ofertantes/{id}")
    public OfertanteProfileDTO getOfertanteById(@PathVariable Integer id) {return adminOfertanteService.findById(id);}

    @PutMapping("/Ofertantes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OfertanteRegisterDTO updateOfertante(@PathVariable Integer id, @Valid @RequestBody OfertanteRegisterDTO ofertanteForm) {
        return adminOfertanteService.update(id, ofertanteForm);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/Ofertantes/{id}/rate")
    public ResponseEntity<OfertanteRegisterDTO> updateReputation(@PathVariable Integer id, @RequestParam Integer ratingValue) {
        if (ratingValue == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Validar que ratingValue esté en el rango permitido (0 o 1)
        if (ratingValue != 0 && ratingValue != 1) {
            return ResponseEntity.badRequest().body(null);
        }

        OfertanteRegisterDTO updatedOfertanteDTO = adminOfertanteService.updateReputation(id, ratingValue);

        // Devolver el DTO en la respuesta
        return ResponseEntity.ok(updatedOfertanteDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/Ofertantes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteOfertante(@PathVariable Integer id) {
        adminOfertanteService.delete(id);
    }




}
