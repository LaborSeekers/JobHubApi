package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Feedback;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f from Feedback f WHERE f.jobOffer.id = :job_offer_id AND f.application.id = :application_id")
    Feedback getFeedbacksByJobOfferAndApplication(@Param("job_offer_id") Integer jobOfferId, @Param("application_id") Integer applicationId);
    @Query("SELECT f from Feedback f WHERE f.jobOffer.id = :job_offer_id")
    List<Feedback> getFeedbacksByJobOffer(@Param("job_offer_id") Integer jobOfferId);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Feedback (job_Offer_ID, application_id, contenido)" + "Values(:job_offer_id, :application_id, :content)" , nativeQuery = true)
    void addFeedback(@Param("job_offer_id") Integer jobOfferId, @Param("application_id") Integer applicationId, @Param("content") String content);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM Feedback f WHERE f.jobOffer.id = :job_offer_id AND f.application.id = :application_id" )
    void removeFeedback(@Param("job_offer_id") Integer jobOfferId, @Param("application_id") Integer applicationId);
}
