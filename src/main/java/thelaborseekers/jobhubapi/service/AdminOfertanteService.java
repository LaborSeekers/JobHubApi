package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.Ofertante;

import java.util.List;

public interface AdminOfertanteService {
    List<Ofertante> findAll();
    Page<Ofertante> paginate(Pageable pageable);
    Ofertante create(Ofertante Ofertante);
    Ofertante findById(Integer id);
    Ofertante update(Integer id, Ofertante updatedOfertante);
    //Servicio para operar la reputacion (0 malo y 1 bueno)
    Ofertante updateReputation(Integer id, Integer ratingValue);
    void delete(Integer id);
}
