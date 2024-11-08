package thelaborseekers.jobhubapi.service;


import thelaborseekers.jobhubapi.model.entity.Language;

import java.util.List;

public interface LanguageService {
    Language createEdu(Language language/*, EducationLevel educationLevel*/);
    List<Language> getAllEducationsByCurriculumId(Integer id);
    Language updateEducation(Integer id,Language updatedlanguage);
    void deleteEducation(Integer id);
}
