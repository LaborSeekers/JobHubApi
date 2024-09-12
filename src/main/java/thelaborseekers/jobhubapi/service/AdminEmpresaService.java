package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.Empresa;

import java.util.List;

public interface AdminEmpresaService {
    List<Empresa> findAll();
    Page<Empresa> paginate(Pageable pageable);
    Empresa create(Empresa postulante);
    Empresa findById(Integer id);
    Empresa update(Integer id, Empresa updatedPostulante);
    void delete(Integer id);
}
