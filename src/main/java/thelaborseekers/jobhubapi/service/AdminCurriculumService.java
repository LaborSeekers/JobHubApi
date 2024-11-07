package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.Curriculum;

import java.util.List;

public interface AdminCurriculumService {
    List<Curriculum> findAll();
    Page<Curriculum> paginate(Pageable pageable);
    Curriculum create(Curriculum curriculum);
    Curriculum findById(Integer id);
    Curriculum update(Integer id, Curriculum updatedCurriculum);
    Curriculum findByPostulanteId(Integer postulanteId);
    void delete(Integer id);

    
}