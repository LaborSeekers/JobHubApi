package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.service.PasswordRecoveryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class PasswordRecoveryController {
    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        boolean result = passwordRecoveryService.sendRecoveryEmail(email);
        if (result) {
            return ResponseEntity.ok("Se envió correctamente la recuperación de cuenta a su correo electrónico.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usted no tiene ninguna cuenta con el correo electrónico.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean result = passwordRecoveryService.resetPassword(token, newPassword);
        if (result) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido o ha expirado.");
        }
    }
}

