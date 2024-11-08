package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.dto.CurriculumDTO;
import thelaborseekers.jobhubapi.model.entity.Curriculum;
import thelaborseekers.jobhubapi.model.entity.User;

import java.util.Optional;

public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
    
    Optional<Curriculum> findByPostulanteId(Integer postulanteId);

}