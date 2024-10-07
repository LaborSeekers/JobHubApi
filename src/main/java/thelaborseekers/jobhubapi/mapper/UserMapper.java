package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.AuthResponseDTO;
import thelaborseekers.jobhubapi.dto.LoginDTO;
import thelaborseekers.jobhubapi.dto.UserProfileDTO;
import thelaborseekers.jobhubapi.dto.UserRegistrationDTO;
import thelaborseekers.jobhubapi.model.entity.User;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public User toEntity(UserRegistrationDTO userRegistrationDTO) {
        return modelMapper.map(userRegistrationDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
      UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
      if (user.getOfertante() != null) {
          userProfileDTO.setName(user.getOfertante().getName());
          userProfileDTO.setLastName(user.getOfertante().getLastName());
          userProfileDTO.setEmpresa(user.getOfertante().getEmpresa());
          userProfileDTO.setBirthday(user.getOfertante().getBirthday());
          userProfileDTO.setPhone(user.getOfertante().getPhone());
      }
      if (user.getPostulante() != null) {
          userProfileDTO.setName(user.getPostulante().getName());
          userProfileDTO.setLastName(user.getPostulante().getLastName());
          userProfileDTO.setBirthday(user.getPostulante().getBirthday());
          userProfileDTO.setPhone(user.getPostulante().getPhone());
      }
        return userProfileDTO;
    }

    // Funciones para LoginDTO
    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO =  new AuthResponseDTO();
        authResponseDTO.setToken(token);

        String firstName = (user.getPostulante() != null) ? user.getPostulante().getName()
                :(user.getOfertante() != null)? user.getOfertante().getName()
                :"Admin";
        String lastName = (user.getPostulante() != null) ? user.getPostulante().getLastName()
                :(user.getOfertante() != null)? user.getOfertante().getLastName()
                :"User";
        authResponseDTO.setName(firstName);
        authResponseDTO.setLastName(lastName);

        authResponseDTO.setRole(user.getRole().getName().name());
        return authResponseDTO;
    }
}
