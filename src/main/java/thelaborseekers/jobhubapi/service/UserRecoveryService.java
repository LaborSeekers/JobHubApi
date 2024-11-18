package thelaborseekers.jobhubapi.service;

import lombok.RequiredArgsConstructor;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.util.EmailUtil;
import thelaborseekers.jobhubapi.util.OtpUtil;
import jakarta.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserRecoveryService {

    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String verifyAccount(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        if (user.getPostulante().getOtp() == null) {
            throw new RuntimeException("No OTP found for this user. Please generate a new OTP.: " + otp);
        }
        if(user.getPostulante().getOtp().equals(otp)) {
            if(Duration.between(user.getPostulante().getOtpGeneratedTime(),LocalDateTime.now()).getSeconds() < (5 * 60)){
                user.getPostulante().setActive(true);
                user.getPostulante().setOtp(null);
                user.getPostulante().setOtpGeneratedTime(null);
                userRepository.save(user);
                return "OTP verified, you can login now. ";
            } else {
                throw new BadRequestException("OTP verification failed. Please regenerate and try again.");
            }
        } else {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

    }

    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        String otp = otpUtil.generateOtp();

        try {
            emailUtil.sendSetPasswordEmail(email, otp);
        } catch (MessagingException e) {
            throw new BadRequestException("Unable to send otp please try again");
        }
        user.getPostulante().setOtp(otp);
        user.getPostulante().setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "OTP send to your email";
    }

    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));

        String otp = otpUtil.generateOtp();
        user.getPostulante().setOtp(otp);
        user.getPostulante().setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        try {
            emailUtil.sendOTPEmail(email, otp);
        } catch (MessagingException e) {
            throw new BadRequestException("Unable to send set a password email please try again");
        }
        return "Please check your email to set new password to your account";
    }

    public String setPassword(String email, String newPassword){
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));


        // Codificar la nueva contraseña antes de almacenarla
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        //user.setPassword(newPassword);
        userRepository.save(user);
        return "New password set successfully login with new password";
    }
}
