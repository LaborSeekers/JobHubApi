package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.CurriculumDTO;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.CurriculumMapper;
import thelaborseekers.jobhubapi.model.entity.Curriculum;
import thelaborseekers.jobhubapi.model.entity.Education;
import thelaborseekers.jobhubapi.model.entity.Language;
import thelaborseekers.jobhubapi.model.entity.WorkExperience;
import thelaborseekers.jobhubapi.repository.CurriculumRepository;
import thelaborseekers.jobhubapi.repository.EducationRepository;
import thelaborseekers.jobhubapi.repository.LanguageRepository;
import thelaborseekers.jobhubapi.repository.WorkExperienceRepository;
import thelaborseekers.jobhubapi.service.AdminCurriculumService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminCurriculumServiceImpl implements AdminCurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final EducationRepository educationRepository;
    private final LanguageRepository languageRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final CurriculumMapper curriculumMapper;

    private final ModelMapper modelMapper;
    @Transactional(readOnly = true)
    @Override
    public List<Curriculum> findAll() {
        return curriculumRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Curriculum> paginate(Pageable pageable) {
        return curriculumRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public CurriculumDTO createCompleteCurriculum(Curriculum curriculum, List<Language> languages, List<Education> educations, List<WorkExperience> workExperiences) {
        Curriculum savedCurriculum = curriculumRepository.save(curriculum);

        //Este forEach agrega los idiomas que hable el postulante en su curriculum y ademas asocia cada uno de esos idiomas con el curriculum en la base de datos
        languages.forEach(language ->{
            language.setCurriculum(savedCurriculum);
            languageRepository.save(language);
        });
        //Mismo caso aqui, pero para educacion
        educations.forEach(education -> {
            education.setCurriculum(savedCurriculum);
            educationRepository.save(education);
        });
        workExperiences.forEach(workExperience -> {
            workExperience.setCurriculum(savedCurriculum);
            workExperienceRepository.save(workExperience);
        });

        savedCurriculum.getLanguages().addAll(languages);
        savedCurriculum.getEducation().addAll(educations);
        savedCurriculum.getWork_experience().addAll(workExperiences);
        return curriculumMapper.toDTO(curriculumRepository.save(savedCurriculum));
    }

    @Transactional
    @Override
    public CurriculumDTO updateCurriculum(Integer id, Curriculum updatedCurriculum, List<Language> updatedLanguages, List<Education> updatedEducation,List<WorkExperience> updatedWorkExperiences) {
        Curriculum CurriculumFromDB = curriculumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curriculum not found with id: " + id));

        // Actualizar los atributos principales del currículum
        CurriculumFromDB.setContent(updatedCurriculum.getContent());

        // Actualizar idiomas
        updateLanguages(CurriculumFromDB, updatedLanguages);

        // Actualizar experiencias laborales
        updateEducation(CurriculumFromDB, updatedEducation);

        updateWorkExperience(CurriculumFromDB,updatedWorkExperiences);

        return curriculumMapper.toDTO(curriculumRepository.save(CurriculumFromDB));
    }

    @Transactional(readOnly = true)
    @Override
    public CurriculumDTO findByUserId(Integer id) {
        Curriculum curriculum =curriculumRepository.findByPostulanteId(id).orElseThrow(() -> new RuntimeException("Curriculum not found with id:" + id));
        CurriculumDTO cv =modelMapper.map(curriculum, CurriculumDTO.class);
        cv.setWorkExperience(curriculum.getWork_experience());
        cv.setLanguages(curriculum.getLanguages());
        cv.setEducation(curriculum.getEducation());
        return cv;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Curriculum curriculum = curriculumRepository.findById(id).orElseThrow(() -> new RuntimeException("Curriculum not found with id:" + id));
        curriculumRepository.delete(curriculum);
    }


    private void updateLanguages(Curriculum curriculum, List<Language> updatedLanguages) {
        if(updatedLanguages !=null){

            List<Language> existingLanguages = curriculum.getLanguages();
            existingLanguages.removeIf(language -> !updatedLanguages.contains(language)); // Elimina los idiomas que ya no están en la lista actualizada

            // Agregar o actualizar los idiomas
            updatedLanguages.forEach(language -> {
                if (!existingLanguages.contains(language)) {
                    language.setCurriculum(curriculum); // Asignar el currículum al idioma
                    languageRepository.save(language); // Guardar el nuevo idioma
                    existingLanguages.add(language); // Añadir a la lista actual
                }
            });
        }

    }

    private void updateEducation(Curriculum curriculum, List<Education> updatedEducations) {
        if(updatedEducations != null) {
            List<Education> existingEducations = curriculum.getEducation();
            existingEducations.removeIf(education -> !updatedEducations.contains(education));

            updatedEducations.forEach(education -> {
                if (!existingEducations.contains(education)) {
                    education.setCurriculum(curriculum);
                    educationRepository.save(education);
                    existingEducations.add(education);
                }
            });
        }

    }

    private void updateWorkExperience(Curriculum curriculum, List<WorkExperience> updatedWorkExperiences) {
        if (updatedWorkExperiences != null)
        {
            List<WorkExperience> existingWorkExperience = curriculum.getWork_experience();
            existingWorkExperience.removeIf(workExperience -> !updatedWorkExperiences.contains(workExperience));

            updatedWorkExperiences.forEach(workExperience -> {
                if (!existingWorkExperience.contains(workExperience)) {
                    workExperience.setCurriculum(curriculum);
                    workExperienceRepository.save(workExperience);
                    existingWorkExperience.add(workExperience);
                }
            });
        }

    }



}