package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Education;
import thelaborseekers.jobhubapi.service.EducationService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/curriculums/education")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
public class EducationController {
    private final EducationService educationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Education create(@RequestBody Education educationForm) {
        return educationService.createEdu(educationForm);
    }

    @GetMapping("/{id}")
    public List<Education> getAllEduByCurriculum (@PathVariable Integer id) {
        return educationService.getAllEducationsByCurriculumId(id);
    }

    @PutMapping("/{id}")
    public Education update(@PathVariable Integer id,@RequestBody Education updatedEducationForm) {
        return educationService.updateEducation(id,updatedEducationForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        educationService.deleteEducation(id);
    }
}
