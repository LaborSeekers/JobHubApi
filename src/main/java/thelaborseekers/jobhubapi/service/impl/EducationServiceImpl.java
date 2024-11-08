package thelaborseekers.jobhubapi.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.model.entity.Education;
import thelaborseekers.jobhubapi.model.entity.EducationLevel;
import thelaborseekers.jobhubapi.model.enums.EduLevel;
import thelaborseekers.jobhubapi.repository.EducationRepository;
import thelaborseekers.jobhubapi.service.EducationService;

import java.util.List;


@RequiredArgsConstructor
@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;

    @Override
    @Transactional
    public Education createEdu(Education education/*, EducationLevel educationLevel*/) {
        /*education.setEduLevel(educationLevel);*/
        return educationRepository.save(education);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Education> getAllEducationsByCurriculumId(Integer id) {
        return educationRepository.findByCurriculumId(id);
    }

    @Transactional
    @Override
    public Education updateEducation(Integer id,Education education) {
        Education educationFromDB = educationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Education not found with id: " + id));
        educationFromDB.setInstitutionName(education.getInstitutionName());
        educationFromDB.setStartDate(education.getStartDate());
        educationFromDB.setEndDate(education.getEndDate());
        educationFromDB.setEduLevel(education.getEduLevel());

        educationFromDB = educationRepository.save(educationFromDB);
        return educationFromDB;
    }

    @Transactional
    @Override
    public void deleteEducation(Integer id) {
        Education educationFromDB = educationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Education not found with id: " + id));
        educationRepository.delete(educationFromDB);
    }
}
