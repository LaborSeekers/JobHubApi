package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    @Query("SELECT s FROM Skill s WHERE s.curriculum.id = :curriculumId")
    List<Skill> findByCurriculumId(@Param("curriculumId") Integer curriculumId);
}
