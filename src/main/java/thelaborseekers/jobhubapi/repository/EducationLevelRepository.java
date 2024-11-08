package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.EducationLevel;
import thelaborseekers.jobhubapi.model.enums.EduLevel;

import java.util.Optional;

public interface EducationLevelRepository extends JpaRepository<EducationLevel, Integer> {
    Optional<EducationLevel> findByName(EduLevel name);
}
