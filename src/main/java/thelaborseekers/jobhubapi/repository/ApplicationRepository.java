package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
