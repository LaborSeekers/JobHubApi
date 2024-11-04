package thelaborseekers.jobhubapi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffersPK;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;
import java.util.Optional;


public interface FavoriteJobOffersRepository extends JpaRepository<FavoriteJobOffers, FavoriteJobOffersPK> {

    @Query("SELECT fjo from FavoriteJobOffers fjo WHERE fjo.postulante.id = :Postulante_id")
    List<FavoriteJobOffers> findByPostulanteId( @Param("Postulante_id")Integer postulanteId);
    @Query("SELECT fjo from FavoriteJobOffers fjo WHERE fjo.jobOffer.id = :job_offer_id")
    List<FavoriteJobOffers> findByJobOfferId( @Param("job_offer_id") Integer jobOfferId);

    Page<FavoriteJobOffers> findByPostulanteId(Integer postulanteId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Favorite_Job_Offers (job_offer_ID, postulante_Id)" + "Values(:jobOfferId, :postulanteId)" , nativeQuery = true)
    void addFavoriteJobOffer(@Param("jobOfferId") Integer jobOfferId,@Param("postulanteId") Integer postulanteId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM FavoriteJobOffers WHERE postulante.id = :postulante_id AND jobOffer.id = :job_offer_id")
    void deleteFavoriteJobOffers(@Param("job_offer_id") Integer jobOfferId, @Param("postulante_id") Integer postulanteId);

    @Transactional(readOnly = true)
    @Query("SELECT fjo from FavoriteJobOffers fjo WHERE fjo.postulante.id = :postulante_id AND fjo.jobOffer.id = :job_offer_id")
    Optional<FavoriteJobOffers> findByJobOfferIdAndPostulanteId(@Param("job_offer_id") Integer jobOfferId, @Param("postulante_id") Integer postulanteId);
}
