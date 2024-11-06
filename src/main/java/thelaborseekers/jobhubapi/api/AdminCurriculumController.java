package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.CurriculumDTO;
import thelaborseekers.jobhubapi.model.entity.Curriculum;
import thelaborseekers.jobhubapi.service.AdminCurriculumService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/curriculums")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
public class AdminCurriculumController {

    private final AdminCurriculumService adminCurriculumService;

    @GetMapping
    public List<Curriculum> list() {
        return adminCurriculumService.findAll();
    }

    @GetMapping("/paginate")
    public Page<Curriculum> paginate(Pageable pageable) {
        return adminCurriculumService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CurriculumDTO create(@RequestBody Curriculum curriculumForm) {
        return adminCurriculumService.createCompleteCurriculum(curriculumForm,curriculumForm.getLanguages(),curriculumForm.getEducation(),curriculumForm.getWork_experience());
    }


    @GetMapping("/{id}")
    public CurriculumDTO get(@PathVariable Integer id) {
        return adminCurriculumService.findByUserId(id);
    }

    @PutMapping("/{id}")
    public CurriculumDTO update(@PathVariable Integer id, @RequestBody Curriculum curriculumForm) {
        return adminCurriculumService.updateCurriculum(id, curriculumForm,curriculumForm.getLanguages(),curriculumForm.getEducation(),curriculumForm.getWork_experience());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminCurriculumService.delete(id);
    }
}