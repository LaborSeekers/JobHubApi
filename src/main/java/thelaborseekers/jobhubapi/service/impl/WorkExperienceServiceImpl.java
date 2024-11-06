package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.model.entity.WorkExperience;
import thelaborseekers.jobhubapi.repository.WorkExperienceRepository;
import thelaborseekers.jobhubapi.service.WorkExperienceService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public WorkExperience createEdu(WorkExperience workExperienceForm) {
        return null;
    }

    @Override
    public List<WorkExperience> getAllEducationsByCurriculumId(Integer id) {
        return workExperienceRepository.findByCurriculumId(id);
    }

    @Override
    public WorkExperience updateEducation(Integer id, WorkExperience updatedWorkExperience) {
        return null;
    }

    @Override
    public void deleteEducation(Integer id) {

    }
}
