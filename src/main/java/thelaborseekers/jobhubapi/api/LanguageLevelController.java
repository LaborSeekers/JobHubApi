package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.model.entity.LanguageLevel;
import thelaborseekers.jobhubapi.service.LanguageLevelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/language_level")
public class LanguageLevelController {
    private final LanguageLevelService languageLevelService;

    @GetMapping()
    public List<LanguageLevel> getLanguageLevels() {
        return languageLevelService.getAllLanguageLevels();
    }
}
