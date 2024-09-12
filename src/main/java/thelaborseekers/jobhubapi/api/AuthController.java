package thelaborseekers.jobhubapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/external")
    public String registerWithExternalAccount(@RequestParam String token) {
        // Lógica para verificar el token y obtener datos de la cuenta externa
        return authService.registerWithExternalAccount(token);
    }
}

