package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Language;
import thelaborseekers.jobhubapi.service.LanguageService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/curriculums/Language")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
public class LanguageController {
    private final LanguageService languageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Language create(@RequestBody Language languageForm) {
        return languageService.createEdu(languageForm);
    }

    @GetMapping("/{id}")
    public List<Language> getAllLanguageByCurriculum(@PathVariable Integer id) {
        return languageService.getAllEducationsByCurriculumId(id);
    }

    @PostMapping("/{id}")
    public Language updateLanguage(@PathVariable Integer id, @RequestBody Language updatedlanguageForm) {
        return languageService.updateEducation(id, updatedlanguageForm);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        languageService.deleteEducation(id);
    }
}
