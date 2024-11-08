package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.CurriculumDTO;
import thelaborseekers.jobhubapi.model.entity.Curriculum;
import thelaborseekers.jobhubapi.model.entity.Education;
import thelaborseekers.jobhubapi.model.entity.Language;
import thelaborseekers.jobhubapi.model.entity.WorkExperience;

import java.util.List;

public interface AdminCurriculumService {
    List<Curriculum> findAll();
    Page<Curriculum> paginate(Pageable pageable);

    //Curriculum create(Curriculum curriculum);
    CurriculumDTO createCompleteCurriculum(Curriculum curriculum, List<Language> languages, List<Education> educations,List<WorkExperience> workExperiences);
    CurriculumDTO updateCurriculum(Integer id, Curriculum updatedCurriculum,List<Language> updatedLanguages, List<Education> updatedEducations,List<WorkExperience> updatedWorkExperiences);
    CurriculumDTO findByUserId(Integer id);
    //Curriculum update(Integer id, Curriculum updatedCurriculum);
    void delete(Integer id);

    
}