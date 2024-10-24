package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thelaborseekers.jobhubapi.model.entity.JobOffer;

import java.util.List;

@Repository
public interface JobOfferFilterRequestRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findByLocation(String location);

    List<JobOffer> findByTitle(String title);

    List<JobOffer> findByLocationAndTitle(String location, String title);
}
