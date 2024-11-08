package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Language;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    @Query("SELECT e FROM Language e WHERE e.curriculum.id = :curriculumId")
    List<Language> findByCurriculumId(@Param("curriculumId") Integer curriculumId);

}
