package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.JobModalityDTO;
import thelaborseekers.jobhubapi.model.entity.JobModality;

@Component
public class JobModalityMapper {
    private final ModelMapper modelMapper;
    public JobModalityMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public JobModalityDTO toDTO(JobModality jobModality) {
        return modelMapper.map(jobModality, JobModalityDTO.class);
    }
    public JobModality toEntity(JobModalityDTO jobModalityDTO) {
        return modelMapper.map(jobModalityDTO, JobModality.class);
    }
}
