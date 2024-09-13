package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.Feedback;
import thelaborseekers.jobhubapi.repository.FeedbackRepository;
import thelaborseekers.jobhubapi.service.AdminFeedbackService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminFeedbackServiceImpl implements AdminFeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    @Override
    public Feedback getFeedbacksByJobOfferAndApplication(Integer jobOfferId, Integer applicationId) {
        return feedbackRepository.getFeedbacksByJobOfferAndApplication(jobOfferId, applicationId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Feedback> getFeedbacksByJobOffer(Integer jobOfferId) {
        return feedbackRepository.getFeedbacksByJobOffer(jobOfferId);
    }

    @Transactional
    @Override
    public void create(Integer jobOfferID, Integer applicationId, String content) {
        feedbackRepository.addFeedback(jobOfferID, applicationId, content);
    }

    @Transactional
    @Override
    public void update(Integer jobOfferID, Integer applicationId, String content) {
        Feedback feedback =  feedbackRepository.getFeedbacksByJobOfferAndApplication(jobOfferID, applicationId);
        feedback.setContent(content);
        feedbackRepository.save(feedback);
    }

    @Transactional
    @Override
    public void delete(Integer jobOfferID, Integer applicationId) {
        feedbackRepository.removeFeedback(jobOfferID, applicationId);
    }
}
