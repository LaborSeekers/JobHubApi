package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.FeedbackCreateDTO;
import thelaborseekers.jobhubapi.dto.FeedbackDetailDTO;
import thelaborseekers.jobhubapi.model.entity.Feedback;

import java.util.List;

public interface AdminFeedbackService {
    FeedbackDetailDTO create (Integer applicationId, String content);
    FeedbackCreateDTO update (Integer applicationId, String content);
    void delete (Integer jobOfferID, Integer applicationId);
    FeedbackDetailDTO getFeedbacksByJobOfferAndApplication(Integer jobOfferId, Integer applicationId);
    List<FeedbackDetailDTO> getFeedbacksByJobOffer(Integer jobOfferId);
    List<FeedbackDetailDTO> getFeedbacksFromPostulante(Integer postulanteId);
}
