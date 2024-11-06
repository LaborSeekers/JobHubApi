package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.model.entity.Language;
import thelaborseekers.jobhubapi.repository.LanguageRepository;
import thelaborseekers.jobhubapi.service.LanguageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    @Override
    @Transactional(readOnly = true)
    public Language createEdu(Language language) {
        return languageRepository.save(language);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Language> getAllEducationsByCurriculumId(Integer id) {
        return languageRepository.findByCurriculumId(id);
    }

    @Transactional
    @Override
    public Language updateEducation(Integer id, Language updatedlanguage) {
        Language languageFromDB = languageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Language not found with id: " + id));
        languageFromDB.setName(updatedlanguage.getName());
        languageFromDB.setLanguageLevel(updatedlanguage.getLanguageLevel());
        languageFromDB =languageRepository.save(languageFromDB);
        return languageFromDB;
    }

    @Transactional
    @Override
    public void deleteEducation(Integer id) {
        Language languageFromDb = languageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Language not found with id: " + id));
        languageRepository.delete(languageFromDb);
    }
}
