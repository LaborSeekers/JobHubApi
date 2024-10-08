package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaLaboralRepository extends JpaRepository<JobOffer, Integer> {

    @Query("SELECT j FROM JobOffer j WHERE (:location IS NULL OR j.location = :location) AND (:modality IS NULL OR j.jobModality = :modality)")
    List<JobOffer> findByLocationAndModality(@Param("location") String location, @Param("modality") String modality);
}
