package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Empresa;
import thelaborseekers.jobhubapi.service.AdminEmpresaService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Empresas")
public class AdminEmpresaController {
    private final AdminEmpresaService adminEmpresaService;
    @GetMapping()
    public List<Empresa> list() {return adminEmpresaService.findAll();
    }

    //@GetMapping("/page")
    //public Page<Empresa> paginate(@PageableDefault(size = 5,sort = "email") Pageable pageable) {
    //    return adminEmpresaService.paginate(pageable);
    //}

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Empresa create(@RequestBody Empresa empresaForm) {
        return adminEmpresaService.create(empresaForm);
    }

    @GetMapping("/{id}")
    public Empresa get(@PathVariable Integer id) {
        return adminEmpresaService.findById(id);
    }

    @PutMapping("/{id}")
    public Empresa update(@PathVariable Integer id, @RequestBody Empresa empresaForm) {
        return adminEmpresaService.update(id, empresaForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminEmpresaService.delete(id);
    }

}



