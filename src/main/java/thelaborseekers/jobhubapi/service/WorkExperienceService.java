package thelaborseekers.jobhubapi.service;


import thelaborseekers.jobhubapi.model.entity.WorkExperience;

import java.util.List;

public interface WorkExperienceService {
    WorkExperience createEdu(WorkExperience workExperienceForm/*, EducationLevel educationLevel*/);
    List<WorkExperience> getAllEducationsByCurriculumId(Integer id);
    WorkExperience updateEducation(Integer id,WorkExperience updatedWorkExperience);
    void deleteEducation(Integer id);
}
