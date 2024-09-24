package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.OfertanteProfileDTO;
import thelaborseekers.jobhubapi.dto.OfertanteRegisterDTO;
import thelaborseekers.jobhubapi.model.entity.Ofertante;

@Component
public class OfertanteMapper {
    private final ModelMapper modelMapper;
    public OfertanteMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public OfertanteRegisterDTO toDTO(Ofertante ofertante) {
        return modelMapper.map(ofertante, OfertanteRegisterDTO.class);
    }
    public Ofertante toEntity(OfertanteRegisterDTO ofertanteRegisterDTO) {
        return modelMapper.map(ofertanteRegisterDTO, Ofertante.class);
    }

    public OfertanteProfileDTO toOfertanteProfileDTO(Ofertante ofertante) {
        return modelMapper.map(ofertante, OfertanteProfileDTO.class);
    }

}
