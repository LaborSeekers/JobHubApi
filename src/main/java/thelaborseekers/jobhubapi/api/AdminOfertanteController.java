package thelaborseekers.jobhubapi.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Ofertantes")
public class AdminOfertanteController {
    private final AdminOfertanteService adminOfertanteService;

    @GetMapping()
    public List<Ofertante> list() {
        return adminOfertanteService.findAll();
    }

    //paginate//


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ofertante create(@RequestBody Ofertante ofertanteForm) {
        return adminOfertanteService.create(ofertanteForm);
    }

    @GetMapping("/{id}")
    public Ofertante get(@PathVariable Integer id) {return adminOfertanteService.findById(id);}

    @PutMapping("/{id}")
    public Ofertante update(@PathVariable Integer id, @RequestBody Ofertante ofertanteForm) {
        return adminOfertanteService.update(id, ofertanteForm);
    }
    @PutMapping("/{id}/{reputation}")
    public Ofertante updateReputation(@PathVariable Integer id, @PathVariable Integer reputation) {
        return adminOfertanteService. updateReputation(id, reputation); //posiblemente esta funcion se elimine ya que, la reputacion se maneja de manera interna
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminOfertanteService.delete(id);
    }
}
