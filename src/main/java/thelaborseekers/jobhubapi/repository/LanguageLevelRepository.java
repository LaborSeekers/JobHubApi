package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.LanguageLevel;

public interface LanguageLevelRepository extends JpaRepository<LanguageLevel, Integer> {
}
