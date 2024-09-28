package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.UserProfileDTO;
import thelaborseekers.jobhubapi.dto.UserRegistrationDTO;

public interface UserService {

    UserProfileDTO registerPostulante(UserRegistrationDTO userRegistrationDTO);
    UserProfileDTO registerOfertante(UserRegistrationDTO userRegistrationDTO);

    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);

    UserProfileDTO getUserProfile(Integer id);



}
