package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.WorkExperience;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {
    @Query("SELECT e FROM WorkExperience e WHERE e.curriculum.id = :curriculumId")
    List<WorkExperience> findByCurriculumId(@Param("curriculumId") Integer curriculumId);

}
