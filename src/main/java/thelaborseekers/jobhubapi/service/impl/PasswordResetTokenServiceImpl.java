package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.integration.notification.email.dto.Mail;
import thelaborseekers.jobhubapi.integration.notification.email.service.EmailService;
import thelaborseekers.jobhubapi.model.entity.PasswordResetToken;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.repository.PasswordResetTokenRepository;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.service.PasswordResetTokenService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Transactional
    @Override
    public void createAndSendPasswordResetToken(String email, String url) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiresAt(10);
        passwordResetTokenRepository.save(passwordResetToken);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user.getEmail());
        model.put("resetUrl", url + '/' +passwordResetToken.getToken());

        Mail mail = emailService.createMail(
                user.getEmail(),
                "JobHub | Restablecer contraseña",
                model,
                mailFrom
        );

        emailService.sendMail(mail, "email/password-reset-template");
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de restablecimeinto de contraseña no encontrado"));
    }

    @Override
    public void removeResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public boolean isValidToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .filter(t -> !t.isExpired())
                .isPresent();
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .filter(t -> !t.isExpired())
                .orElseThrow(() -> new ResourceNotFoundException("Token de invalido o expirado"));

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);
    }
}
