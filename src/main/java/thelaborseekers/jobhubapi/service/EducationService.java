package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.Education;

import java.util.List;

public interface EducationService {
    Education createEdu(Education education/*, EducationLevel educationLevel*/);
    List<Education> getAllEducationsByCurriculumId(Integer id);
    Education updateEducation(Integer id,Education education);
    void deleteEducation(Integer id);

}
