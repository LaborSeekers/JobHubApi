package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.model.entity.EducationLevel;
import thelaborseekers.jobhubapi.service.EducationLevelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/education_level")
public class EducationLevelController {
    private final EducationLevelService educationLevelService;

    @GetMapping()
    public List<EducationLevel> getEducationLevels() {
        return educationLevelService.getAllEducationLevels();
    }
}
