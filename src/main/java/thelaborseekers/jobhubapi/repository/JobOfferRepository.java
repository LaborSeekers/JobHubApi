package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.JobOffer;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    @Query("SELECT j FROM JobOffer j WHERE (:location IS NULL OR j.location = :location) AND (:category IS NULL OR j.category = :category)")
    List<JobOffer> findByLocationAndCategory(@Param("location") String location, @Param("category") String category);
}

