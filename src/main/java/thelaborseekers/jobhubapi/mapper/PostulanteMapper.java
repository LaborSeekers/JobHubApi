package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.PostulanteRegisterDTO;
import thelaborseekers.jobhubapi.dto.PostulanteProfileDTO;
import thelaborseekers.jobhubapi.model.entity.Postulante;

@Component
public class PostulanteMapper {

    private final ModelMapper modelMapper;
    public PostulanteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public PostulanteRegisterDTO toDTO (Postulante postulante) {
        return modelMapper.map(postulante, PostulanteRegisterDTO.class);
    }
    public Postulante toEntity (PostulanteRegisterDTO postulanteRegisterDTO) {
        return modelMapper.map(postulanteRegisterDTO, Postulante.class);
    }


    // Convertimos el postulante a su DTO perfil
    public PostulanteProfileDTO toPostulanteProfileDTO(Postulante postulante) {
       //PostulanteProfileDTO userProfileDTO = modelMapper.map(postulante, PostulanteProfileDTO.class);

       //userProfileDTO.setName(postulante.getName());
       //userProfileDTO.setLastName(postulante.getLastName());
       //userProfileDTO.setEmail(postulante.getEmail());
       //userProfileDTO.setPhone(postulante.getPhone());
       //userProfileDTO.setBirthday(postulante.getBirthday());
        //return userProfileDTO;
        return modelMapper.map(postulante, PostulanteProfileDTO.class);
    }

}
