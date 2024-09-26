package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.JobModalityDTO;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.service.AdminJobModalityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/JobModalities")
public class AdminJobModalityController {
    private final AdminJobModalityService adminJobModalityService;

    @GetMapping
    public List<JobModalityDTO> list() {
        return adminJobModalityService.findAll();
    }

    //Paginate

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JobModalityDTO create(@Valid @RequestBody JobModalityDTO jobModality) {
        return adminJobModalityService.create(jobModality);
    }

    @GetMapping("/{id}")
    public JobModalityDTO get(@PathVariable Integer id) {
        return adminJobModalityService.findById(id);
    }

    @PutMapping("/{id}")
    public JobModalityDTO update(@PathVariable Integer id,@Valid @RequestBody JobModalityDTO jobModalityForm) {

        return adminJobModalityService.update(id, jobModalityForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminJobModalityService.delete(id);
    }
}
