package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.JobModality;

public interface JobModalityRepository extends JpaRepository<JobModality, Integer> {
}
