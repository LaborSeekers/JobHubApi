package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.OfertanteProfileDTO;
import thelaborseekers.jobhubapi.dto.OfertanteRegisterDTO;
import thelaborseekers.jobhubapi.model.entity.Ofertante;

import java.util.List;

public interface AdminOfertanteService {
    List<OfertanteProfileDTO> findAll();
    Page<OfertanteProfileDTO> paginate(Pageable pageable);
    OfertanteRegisterDTO create(OfertanteRegisterDTO Ofertante);
    OfertanteProfileDTO findById(Integer id);
    OfertanteRegisterDTO update(Integer id,OfertanteRegisterDTO updatedOfertante);
    //Servicio para operar la reputacion (0 malo y 1 bueno)
    OfertanteRegisterDTO updateReputation(Integer id, Integer ratingValue);
    void delete(Integer id);
}
