package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public Curriculum create(@RequestBody Curriculum curriculumForm) {
        return adminCurriculumService.create(curriculumForm);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
    @GetMapping("/{id}")
    public Curriculum get(@PathVariable Integer id) {
        return adminCurriculumService.findById(id);
    }

    @PutMapping("/{id}")
    public Curriculum update(@PathVariable Integer id, @RequestBody Curriculum curriculumForm) {
        return adminCurriculumService.update(id, curriculumForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminCurriculumService.delete(id);
    }
    @GetMapping("/user/{postulanteId}")
    public Curriculum getByUserId(@PathVariable Integer postulanteId) {
    return adminCurriculumService.findByPostulanteId(postulanteId);
}
}