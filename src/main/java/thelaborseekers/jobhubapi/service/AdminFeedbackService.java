package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.Feedback;

import java.util.List;

public interface AdminFeedbackService {
    void create (Integer jobOfferID, Integer applicationId, String content);
    void update (Integer jobOfferID, Integer applicationId, String content);
    void delete (Integer jobOfferID, Integer applicationId);
    Feedback getFeedbacksByJobOfferAndApplication(Integer jobOfferId, Integer applicationId);
    List<Feedback> getFeedbacksByJobOffer(Integer jobOfferId);

}
