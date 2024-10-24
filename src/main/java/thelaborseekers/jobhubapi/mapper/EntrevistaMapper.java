package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.EntrevistaCreateDTO;
import thelaborseekers.jobhubapi.dto.EntrevistaDetailDTO; // Asegúrate de que este DTO exista
import thelaborseekers.jobhubapi.model.entity.Entrevista;

@Component
public class EntrevistaMapper {

    private final ModelMapper modelMapper;

    public EntrevistaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // Response
    public EntrevistaDetailDTO toEntrevistaDetailDTO(Entrevista entrevista) {
        EntrevistaDetailDTO dto = modelMapper.map(entrevista, EntrevistaDetailDTO.class);
        dto.setPostulanteName(entrevista.getPostulante().getFirstName() + " " + entrevista.getPostulante().getLastName());
        dto.setOfertanteName(entrevista.getOfertante().getFirstName() + " " + entrevista.getOfertante().getLastName());

        return dto;
    }

    public Entrevista toEntrevista(EntrevistaCreateDTO entrevistaCreateDTO) {
        return modelMapper.map(entrevistaCreateDTO, Entrevista.class);
    }

    // Request
    public EntrevistaCreateDTO toEntrevistaCreateDTO(Entrevista entrevista) {
        return modelMapper.map(entrevista, EntrevistaCreateDTO.class);
    }
}
