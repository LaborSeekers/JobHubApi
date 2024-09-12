package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;

public interface AdminPostulanteService {
    List<Postulante> findAll();
    Page<Postulante> paginate(Pageable pageable);
    Postulante create(Postulante postulante);
    Postulante findById(Integer id);
    Postulante update(Integer id, Postulante updatedPostulante);
    void delete(Integer id);

     // Nuevos métodos
     List<Postulante> filterByNameAndLastName(String name, String lastName);
     List<Postulante> filterByAge(int age);
}
