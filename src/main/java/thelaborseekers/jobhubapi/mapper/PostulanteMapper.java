package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.CurriculumDTO;
import thelaborseekers.jobhubapi.dto.PostulanteRegisterDTO;
import thelaborseekers.jobhubapi.dto.PostulanteProfileDTO;
import thelaborseekers.jobhubapi.model.entity.Curriculum;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.CurriculumRepository;

import java.util.Optional;

@Component
public class PostulanteMapper {

    private final ModelMapper modelMapper;
    private final CurriculumRepository curriculumRepository;

    public PostulanteMapper(ModelMapper modelMapper, CurriculumRepository curriculumRepository) {
        this.modelMapper = modelMapper;
        this.curriculumRepository = curriculumRepository;
    }
    public PostulanteRegisterDTO toDTO (Postulante postulante) {
        return modelMapper.map(postulante, PostulanteRegisterDTO.class);
    }
    public Postulante toEntity (PostulanteRegisterDTO postulanteRegisterDTO) {
        return modelMapper.map(postulanteRegisterDTO, Postulante.class);
    }


    // Convertimos el postulante a su DTO perfil
    public PostulanteProfileDTO toPostulanteProfileDTO(Postulante postulante) {

        Curriculum cv = curriculumRepository.findByPostulanteId(postulante.getId()).orElse(null);
        PostulanteProfileDTO ProfileDTO = modelMapper.map(postulante, PostulanteProfileDTO.class);
        ProfileDTO.setEmail(postulante.getUser().getEmail());
        ProfileDTO.setUserId(postulante.getUser().getId());
        if (cv != null) {
            CurriculumDTO CurriculumCompleto =modelMapper.map(cv,CurriculumDTO.class);
            CurriculumCompleto.setEducation(cv.getEducation());
            CurriculumCompleto.setLanguages(cv.getLanguages());
            CurriculumCompleto.setWorkExperience(cv.getWork_experience());
            ProfileDTO.setCurriculum(CurriculumCompleto);
        }
        return ProfileDTO;
    }

    public PostulanteProfileDTO toProfileDTO(Postulante postulante) {
        // Buscar el CV si existe
        Optional<Curriculum> cvOpt = curriculumRepository.findByPostulanteId(postulante.getId());
        PostulanteProfileDTO profileDTO = modelMapper.map(postulante, PostulanteProfileDTO.class);
    
        // Si el CV existe, lo mapeamos
        cvOpt.ifPresent(cv -> profileDTO.setCurriculum(modelMapper.map(cv, CurriculumDTO.class)));
    
        return profileDTO;
    }

}
