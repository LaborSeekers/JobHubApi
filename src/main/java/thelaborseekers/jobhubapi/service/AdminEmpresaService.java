package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.EmpresaDTO;

import java.util.List;

public interface AdminEmpresaService {
    List<EmpresaDTO> findAll();
    Page<EmpresaDTO> paginate(Pageable pageable);
    EmpresaDTO create(EmpresaDTO postulante);
    EmpresaDTO findById(Integer id);
    EmpresaDTO update(Integer id, EmpresaDTO updatedPostulante);
    void delete(Integer id);
}
