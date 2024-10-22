package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.CurriculumDTO;
import thelaborseekers.jobhubapi.model.entity.Curriculum;

@Component
public class CurriculumMapper {
    private final ModelMapper modelMapper;
    public CurriculumMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}
    public CurriculumDTO toDTO(Curriculum curriculum) {
        return modelMapper.map(curriculum, CurriculumDTO.class);
    }
    public Curriculum toEntity(CurriculumDTO curriculumDTO) {
        return modelMapper.map(curriculumDTO, Curriculum.class);
    }

}
