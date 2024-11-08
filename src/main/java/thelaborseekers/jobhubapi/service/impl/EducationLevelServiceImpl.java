package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.EducationLevel;
import thelaborseekers.jobhubapi.repository.EducationLevelRepository;
import thelaborseekers.jobhubapi.service.EducationLevelService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationLevelServiceImpl implements EducationLevelService {

    private final EducationLevelRepository educationLevelRepository;


    @Transactional(readOnly = true)
    @Override
    public List<EducationLevel> getAllEducationLevels() {
        return educationLevelRepository.findAll();
    }
}
