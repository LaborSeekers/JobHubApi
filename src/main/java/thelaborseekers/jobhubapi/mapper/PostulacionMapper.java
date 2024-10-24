package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.PostulacionDTO;
import thelaborseekers.jobhubapi.model.entity.Postulacion;

@Component
public class PostulacionMapper {
    private final ModelMapper modelMapper;

    public PostulacionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PostulacionDTO toDTO(Postulacion postulacion) {
        PostulacionDTO postulacionDTO = modelMapper.map(postulacion, PostulacionDTO.class);
        postulacionDTO.setPostulanteNombre(postulacion.getPostulante().getFirstName() + " " + postulacion.getPostulante().getLastName());
        postulacionDTO.setOfertaLaboralTitulo(postulacion.getOfertaLaboral().getTitle());
        return postulacionDTO;
    }

}
