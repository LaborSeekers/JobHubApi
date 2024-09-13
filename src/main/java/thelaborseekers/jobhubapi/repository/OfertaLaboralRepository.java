package thelaborseekers.jobhubapi.repository;

import thelaborseekers.jobhubapi.model.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaLaboralRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findByUbicationAndCategory(String ubication, String category);
}
