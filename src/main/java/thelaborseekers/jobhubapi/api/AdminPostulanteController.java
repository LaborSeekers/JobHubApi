package thelaborseekers.jobhubapi.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.service.AdminPostulanteService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Postulantes")
public class AdminPostulanteController {
    private final AdminPostulanteService adminPostulanteService;

    @GetMapping()
    public List<Postulante> list(){return adminPostulanteService.findAll();}


    @GetMapping("/page")
    public Page<Postulante> paginate(@PageableDefault(size = 5,sort = "email") Pageable pageable) {
        return adminPostulanteService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Postulante create(@RequestBody Postulante postulanteForm) {return adminPostulanteService.create(postulanteForm);}

    @GetMapping("/{id}")
    public Postulante get(@PathVariable Integer id) {return adminPostulanteService.findById(id);}

    @PutMapping("/{id}")
    public Postulante update(@PathVariable Integer id, @RequestBody Postulante postulanteForm) {
        return adminPostulanteService.update(id, postulanteForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminPostulanteService.delete(id);
    }

    @GetMapping("/filter")
    public List<Postulante> filterByNameAndLastName(@RequestParam String name, @RequestParam String lastName) {
        return adminPostulanteService.filterByNameAndLastName(name, lastName);
    }

    @GetMapping("/filterByAge")
    public List<Postulante> filterByAge(@RequestParam int age) {
        return adminPostulanteService.filterByAge(age);
    }

}
