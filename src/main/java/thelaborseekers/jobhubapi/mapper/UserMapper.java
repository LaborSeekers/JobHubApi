package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
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

}
