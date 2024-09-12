package thelaborseekers.jobhubapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;

import java.util.UUID;
@Service
public class PasswordRecoveryService {

    @Autowired
    private PostulanteRepository postulanteRepository;

    public boolean sendRecoveryEmail(String email) {
        Postulante postulante = postulanteRepository.findByEmail(email);
        if (postulante != null) {
            // Generar un token seguro
            String token = generateRecoveryToken();
            postulante.setRecoveryToken(token);
            postulanteRepository.save(postulante);

            // Enviar el correo con el enlace de recuperación
            sendRecoveryEmailToUser(postulante.getEmail(), token);

            return true;
        }
        return false;
    }

    private String generateRecoveryToken() {
        // Generar un token único y seguro
        return UUID.randomUUID().toString();
    }

    private void sendRecoveryEmailToUser(String email, String token) {
        String recoveryLink = "http://your-domain.com/recover-password?token=" + token;

        //SimpleMailMessage message = new SimpleMailMessage();
        //message.setTo(email);
        //message.setSubject("Recuperación de Contraseña");
        //message.setText("Para recuperar tu contraseña, haz clic en el siguiente enlace: " + recoveryLink);

        //mailSender.send(message);
    }

    public boolean resetPassword(String token, String newPassword) {
        Postulante postulante = postulanteRepository.findByRecoveryToken(token);
        if (postulante != null) {
            postulante.setPassword(newPassword);
            postulante.setRecoveryToken(null); // Limpiar el token después de usarlo
            postulanteRepository.save(postulante);
            return true;
        }
        return false;
    }
}



