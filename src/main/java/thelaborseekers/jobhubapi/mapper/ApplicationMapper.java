package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.ApplicationCreateDTO;
import thelaborseekers.jobhubapi.dto.ApplicationDetailDTO;
import thelaborseekers.jobhubapi.model.entity.Application;

@Component
public class ApplicationMapper {
    private final ModelMapper modelMapper;
    public ApplicationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ApplicationDetailDTO toDTO(Application application) {
        ApplicationDetailDTO dto = this.modelMapper.map(application, ApplicationDetailDTO.class);
        dto.setJobOffer_id(application.getJobOffer().getId());
        dto.setPostulante_id(application.getPostulante().getId());
        return dto;
    }

    public Application toEntity(ApplicationCreateDTO applicationCreateDTO) {
        return this.modelMapper.map(applicationCreateDTO, Application.class);
    }
}
