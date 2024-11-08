package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.PostulanteRegisterDTO;
import thelaborseekers.jobhubapi.dto.PostulanteHistorialDTO;
import thelaborseekers.jobhubapi.dto.PostulanteProfileDTO;

import java.util.List;

public interface AdminPostulanteService {
    List<PostulanteProfileDTO> findAll();
    Page<PostulanteRegisterDTO> paginate(Pageable pageable);
    PostulanteRegisterDTO create(PostulanteRegisterDTO postulante);
    PostulanteProfileDTO findById(Integer id);
    PostulanteRegisterDTO update(Integer id, PostulanteRegisterDTO updatedPostulanteRegisterDTO);
    void delete(Integer id);
    List<PostulanteProfileDTO> findByIds(List<Integer> id);
    // Nuevos métodos
     List<PostulanteProfileDTO> filterByNameAndLastName(String name, String lastName);
     List<PostulanteProfileDTO> filterByAge(int age);
     PostulanteHistorialDTO getHistorialPostulaciones(Integer postulanteId);
}
