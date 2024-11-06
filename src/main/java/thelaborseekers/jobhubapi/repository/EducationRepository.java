package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Education;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    @Query("SELECT e FROM Education e WHERE e.curriculum.id = :curriculumId")
    List<Education> findByCurriculumId(@Param("curriculumId") Integer curriculumId);

}
