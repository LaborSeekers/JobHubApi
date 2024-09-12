package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.JobOffer;

public interface JobOfferRepository extends JpaRepository<JobOffer,Integer> {
}
