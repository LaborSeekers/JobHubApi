package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    void createAndSendPasswordResetToken(String email, String url) throws Exception;
    PasswordResetToken findByToken(String token);
    void removeResetToken(PasswordResetToken passwordResetToken);
    boolean isValidToken(String token);
    void resetPassword(String token, String newPassword);
}
