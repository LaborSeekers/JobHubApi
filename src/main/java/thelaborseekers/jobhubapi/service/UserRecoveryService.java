package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.LoginDto;
import thelaborseekers.jobhubapi.dto.RegisterDto;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.util.EmailUtil;
import thelaborseekers.jobhubapi.util.OtpUtil;
import jakarta.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserRecoveryService {
    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private PostulanteRepository userRepository;
    @Autowired
    private PostulanteRepository postulanteRepository;

    public String register(RegisterDto registerDto) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendSetPasswordEmail(registerDto.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        Postulante user = new Postulante();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }

    public String verifyAccount(String email, String otp) {
        Postulante user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        Postulante user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    public String login(LoginDto loginDto) {
        Postulante user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
        if (!loginDto.getPassword().equals(user.getPassword())) {
            return "Password is incorrect";
        } else if (!user.isActive()) {
            return "your account is not verified";
        }
        return "Login successful";
    }

    public String forgotPassword(String email) {
        Postulante user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send set a password email please try again");
        }
        return "Please check your email to set new password to your account";
    }

    public String setPassword(String email, String newPassword){
        Postulante user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + email));
        user.setPassword(newPassword);
        userRepository.save(user);
        return "New password set successfully login with new password";
    }
}
