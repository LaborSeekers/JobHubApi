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
    Ofertante updateReputation(Integer id, Integer reputation);
    void delete(Integer id);
}
