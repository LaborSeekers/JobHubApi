package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.LanguageLevel;
import thelaborseekers.jobhubapi.repository.LanguageLevelRepository;
import thelaborseekers.jobhubapi.service.LanguageLevelService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageLevelServiceImpl implements LanguageLevelService {
    private final LanguageLevelRepository languageLevelRepository;

    @Transactional(readOnly = true)
    @Override
    public List<LanguageLevel> getAllLanguageLevels() {
        return languageLevelRepository.findAll();
    }
}
