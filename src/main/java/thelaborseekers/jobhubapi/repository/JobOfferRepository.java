package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.JobOffer;

import java.util.List;
import java.util.Optional;

public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    Optional<JobOffer> findByTitle(String title);

    @Query("SELECT j FROM JobOffer j WHERE (:location IS NULL OR j.location = :location) AND (:modality IS NULL OR j.jobModality = :modality)")
    List<JobOffer> findByLocationAndModality(@Param("location") String location, @Param("modality") Integer modality);

    List<JobOffer> findByLocation(String location);
}
