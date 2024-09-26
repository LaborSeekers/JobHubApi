package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thelaborseekers.jobhubapi.model.entity.JobModality;

import java.util.Optional;

public interface JobModalityRepository extends JpaRepository<JobModality, Integer> {
    Optional<JobModality> findByName(String name);
}
