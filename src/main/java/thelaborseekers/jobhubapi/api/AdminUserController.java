package thelaborseekers.jobhubapi.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.service.AdminUserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping()
    public List<Postulante> list(){return adminUserService.findAll();}


    @GetMapping("/page")
    public Page<Postulante> paginate(@PageableDefault(size = 5,sort = "email") Pageable pageable) {
        return adminUserService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Postulante create(@RequestBody Postulante postulanteForm) {return adminUserService.create(postulanteForm);}

    @GetMapping("/{id}")
    public Postulante get(@PathVariable Integer id) {return adminUserService.findById(id);}

    @PutMapping("/{id}")
    public Postulante update(@PathVariable Integer id, @RequestBody Postulante postulanteForm) {
        return adminUserService.update(id, postulanteForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {adminUserService.delete(id);}

}
