package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.service.AdminJobModalityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/JobModalities")
public class AdminJobModalityController {
    private final AdminJobModalityService adminJobModalityService;

    @GetMapping
    public List<JobModality> list() {
        return adminJobModalityService.findAll();
    }

    //Paginate

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JobModality create(@RequestBody JobModality jobModality) {
        return adminJobModalityService.create(jobModality);
    }

    @GetMapping("/{id}")
    public JobModality get(@PathVariable Integer id) {
        return adminJobModalityService.findById(id);
    }

    @PutMapping("/{id}")
    public JobModality update(@PathVariable Integer id, @RequestBody JobModality jobModalityForm) {

        return adminJobModalityService.update(id, jobModalityForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminJobModalityService.delete(id);
    }
}
