package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.LoginDTO;
import thelaborseekers.jobhubapi.dto.RegisterDto;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.util.EmailUtil;
import thelaborseekers.jobhubapi.util.OtpUtil;
import jakarta.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserRecoveryService {
    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private PostulanteRepository postulanteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    public String register(RegisterDto registerDto) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendSetPasswordEmail(registerDto.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        User user = new User();
        user.getPostulante().setFirstName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.getPostulante().setOtp(otp);
        user.getPostulante().setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }*/

    public String verifyAccount(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        if (user.getPostulante().getOtp() == null) {
            throw new RuntimeException("No OTP found for this user. Please generate a new OTP.: " + otp);
        }
        if(user.getPostulante().getOtp().equals(otp)) {
            if(Duration.between(user.getPostulante().getOtpGeneratedTime(),LocalDateTime.now()).getSeconds() < (1 * 60)){
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

    public String login(LoginDTO loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));

        //Verificar si la cuenta ha sido verificada(active=true)

        if(!user.getPostulante().getActive()){
            throw new BadRequestException("Your account is not verified. Please verify your account using the OTP sent to your email.");
        }

        if (!loginDto.getPassword().equals(user.getPassword())) {
            throw new BadRequestException("Invalid password.");
        } else {
            return "Login successful";
        }

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

        /*
        if(!user.getPostulante().getActive()){
            throw new BadRequestException("Para restablecer tu contraseña primero verifica tu cuenta");
        }*/

        // Codificar la nueva contraseña antes de almacenarla
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        //user.setPassword(newPassword);
        userRepository.save(user);
        return "New password set successfully login with new password";
    }
}
