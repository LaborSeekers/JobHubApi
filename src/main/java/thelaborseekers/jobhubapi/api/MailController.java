package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.service.PasswordResetTokenService;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/sendMail")
    public ResponseEntity<Void> sendPasswordResetMail(@RequestBody String email, @RequestParam String url) throws Exception {
        passwordResetTokenService.createAndSendPasswordResetToken(email, url);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reset/check/{token}")
    public ResponseEntity<Boolean> checkPasswordResetToken(@PathVariable("token") String token) {
        boolean isValid = passwordResetTokenService.isValidToken(token);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }

    @PostMapping("/reset/{token}")
    public ResponseEntity<Void> resetPassword(@PathVariable("token") String token, @RequestBody String newPassword) {
        passwordResetTokenService.resetPassword(token, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
