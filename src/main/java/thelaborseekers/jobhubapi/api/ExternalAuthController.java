package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.service.ExternalAuthService;
import thelaborseekers.jobhubapi.model.dto.ExternalAccountDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class ExternalAuthController {

    private final ExternalAuthService externalAuthService;

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
}
