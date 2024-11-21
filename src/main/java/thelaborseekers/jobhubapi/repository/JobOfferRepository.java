package thelaborseekers.jobhubapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import thelaborseekers.jobhubapi.dto.CategoryChartDTO;
import thelaborseekers.jobhubapi.dto.JobOfferAplicantsDTO;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.enums.JobStatus;

import java.util.List;
import java.util.Optional;

public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    List<JobOffer> findByOfertanteEmpresaId(Integer companyId);

    Optional<JobOffer> findByTitle(String title);

    @Query("SELECT j FROM JobOffer j WHERE j.id in :ids AND (:location IS NULL OR j.location ILIKE :location) AND (:modality IS NULL OR j.jobModality.id = :modality) AND (:status IS NULL OR j.status = :status) AND (:title IS NULL OR j.title ILIKE :title)")
    Page<JobOffer> findByIds(@Param("ids") List<Integer> ids, @Param("location") String location, @Param("modality") Integer modality, @Param("status") JobStatus status, @Param("title") String title, Pageable pageable);

    @Query("SELECT j FROM JobOffer j WHERE (:location IS NULL OR j.location ILIKE :location) AND (:modality IS NULL OR j.jobModality.id = :modality) AND (:status IS NULL OR j.status = :status) AND (:title IS NULL OR j.title ILIKE :title)")
    Page<JobOffer> findByLocationAndModalityAndStatusAndTitle(@Param("location") String location, @Param("modality") Integer modality, @Param("status") JobStatus status, @Param("title") String title, Pageable pageable);

    @Query("SELECT j FROM  JobOffer j WHERE j.status = :status")
    List<JobOffer> findAllActive(@Param("status") JobStatus status);

    @Query("SELECT j FROM JobOffer j WHERE (:location IS NULL OR j.location ILIKE :location) " +
            "AND (:modality IS NULL OR j.jobModality.id = :modality) " +
            "AND (:status IS NULL OR j.status = :status) " +
            "AND (:title IS NULL OR j.title ILIKE :title) " +
            "AND (:ofertanteId IS NULL OR j.ofertante.id = :ofertanteId)")
    Page<JobOffer> findByLocationAndModalityAndStatusAndTitleAndOfertanteId(
            @Param("location") String location,
            @Param("modality") Integer modality,
            @Param("status") JobStatus status,
            @Param("title") String title,
            @Param("ofertanteId") Integer ofertanteId,
            Pageable pageable);

    //Reporte
    @Query("SELECT new thelaborseekers.jobhubapi.dto.JobOfferAplicantsDTO(j.title, COUNT(a)) " +
            "FROM JobOffer j LEFT JOIN Application a ON a.jobOffer.id = j.id " + // Cambié JOIN por LEFT JOIN
            "WHERE j.ofertante.id = :ofertanteId " +
            "GROUP BY j.id")
    List<JobOfferAplicantsDTO> findJobOffersWithApplicantsCountByOfertanteId(@Param("ofertanteId") Integer ofertanteId);
 //Grafico
 @Query("SELECT new thelaborseekers.jobhubapi.dto.CategoryChartDTO(j.status, COUNT(j)) " +
       "FROM JobOffer j GROUP BY j.status")
List<CategoryChartDTO> countJobOffersByStatus();

@Query("SELECT new thelaborseekers.jobhubapi.dto.CategoryChartDTO(j.location, COUNT(j)) " +
       "FROM JobOffer j GROUP BY j.location")
List<CategoryChartDTO> countJobOffersByLocation();

// Gráfico por modalidad
@Query("SELECT new thelaborseekers.jobhubapi.dto.CategoryChartDTO(jm.name, COUNT(j)) " +
       "FROM JobOffer j JOIN j.jobModality jm GROUP BY jm.name")
List<CategoryChartDTO> countJobOffersByModality();


}
