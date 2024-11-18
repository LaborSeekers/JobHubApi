package thelaborseekers.jobhubapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Optional<Application> findByJobOfferIdAndPostulanteId(Integer jobOfferId, Integer postulanteId);
    @Query("SELECT a FROM Application a WHERE a.postulante.id = :postulanteId ORDER BY a.dateUpdated DESC")
    List<Application> findByPostulanteId(@Param("postulanteId") Integer postulanteId);

    @Query("SELECT a FROM Application a WHERE a.jobOffer.id = :jobofferId ORDER BY a.dateUpdated DESC")
    List<Application> findByJobOfferId(@Param("jobofferId") Integer jobofferId);
    Page<Application> findByPostulanteId(Integer postulanteId, Pageable pageable);
    Page<Application> findByJobOfferId(Integer jobofferId, Pageable pageable);
}
