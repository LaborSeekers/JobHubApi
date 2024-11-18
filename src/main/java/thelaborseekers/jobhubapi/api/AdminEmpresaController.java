package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.EmpresaDTO;
import thelaborseekers.jobhubapi.service.AdminEmpresaService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Empresas")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminEmpresaController {
    private final AdminEmpresaService adminEmpresaService;
    @GetMapping()
    public List<EmpresaDTO> list() {return adminEmpresaService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmpresaDTO create(@Valid @RequestBody EmpresaDTO empresaForm) {
        return adminEmpresaService.create(empresaForm);
    }

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @GetMapping("/{id}")
    public EmpresaDTO get(@PathVariable Integer id) {
        return adminEmpresaService.findById(id);
    }

    @PutMapping("/{id}")
    public EmpresaDTO update(@PathVariable Integer id, @Valid @RequestBody EmpresaDTO empresaForm) {
        return adminEmpresaService.update(id, empresaForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminEmpresaService.delete(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @GetMapping("/{jobOfferId}/empresa")
    public ResponseEntity<EmpresaDTO> getEmpresaByJobOfferId(@PathVariable Integer jobOfferId) {
        EmpresaDTO empresaDTO = adminEmpresaService.getEmpresaByJobOfferId(jobOfferId);
        return ResponseEntity.ok(empresaDTO);
    }

}



