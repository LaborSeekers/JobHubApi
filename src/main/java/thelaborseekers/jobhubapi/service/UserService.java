package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.AuthResponseDTO;
import thelaborseekers.jobhubapi.dto.LoginDTO;
import thelaborseekers.jobhubapi.dto.UserProfileDTO;
import thelaborseekers.jobhubapi.dto.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    UserProfileDTO registerPostulante(UserRegistrationDTO userRegistrationDTO);
    UserProfileDTO registerOfertante(UserRegistrationDTO userRegistrationDTO);

    AuthResponseDTO login(LoginDTO loginDTO);

    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfile(Integer id);


    List<UserProfileDTO> getAllUserProfiles();



}
