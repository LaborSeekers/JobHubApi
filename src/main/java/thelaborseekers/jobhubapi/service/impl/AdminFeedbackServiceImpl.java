package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.FeedbackCreateDTO;
import thelaborseekers.jobhubapi.dto.FeedbackDetailDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.FeedbackMapper;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.Feedback;
import thelaborseekers.jobhubapi.repository.ApplicationRepository;
import thelaborseekers.jobhubapi.repository.FeedbackRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.service.AdminFeedbackService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminFeedbackServiceImpl implements AdminFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional(readOnly = true)
    @Override
    public FeedbackDetailDTO getFeedbacksByJobOfferAndApplication(Integer jobOfferId, Integer applicationId) {
        Feedback feedback = feedbackRepository.getFeedbacksByJobOfferAndApplication(jobOfferId, applicationId);
        if (feedback == null) {
            throw new ResourceNotFoundException("No existe un feedback con esos id");
        }
        return feedbackMapper.toDetailDTO(feedback);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FeedbackDetailDTO> getFeedbacksByJobOffer(Integer jobOfferId) {
        List<Feedback> fjo = feedbackRepository.getFeedbacksByJobOffer(jobOfferId);
        return fjo.stream()
                .map(feedbackMapper::toDetailDTO)
                .toList();
    }

    @Transactional
    @Override
    public FeedbackCreateDTO create(Integer jobOfferID, Integer applicationId, String content) {
        Feedback feedback = feedbackRepository.getFeedbacksByJobOfferAndApplication(jobOfferID, applicationId);
        if (feedback != null) {
            throw new BadRequestException("Ya existe feedback con esos id");
        }

        jobOfferRepository.findById(jobOfferID).orElseThrow(
                () -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferID));
        applicationRepository.findById(applicationId).orElseThrow(
                () -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        feedbackRepository.addFeedback(jobOfferID, applicationId, content);

        FeedbackCreateDTO feedbackCreateDTO = new FeedbackCreateDTO();
        feedbackCreateDTO.setApplicationID(applicationId);
        feedbackCreateDTO.setJobOfferID(jobOfferID);
        feedbackCreateDTO.setContent(content);

        return feedbackCreateDTO;
    }

    @Transactional
    @Override
    public FeedbackCreateDTO update(Integer jobOfferID, Integer applicationId, String content) {
        Feedback feedback =  feedbackRepository.getFeedbacksByJobOfferAndApplication(jobOfferID, applicationId);
        if (feedback == null) {
            throw new ResourceNotFoundException("No existe un feedback con esos id");
        }

        feedback.setContent(content);
        feedbackRepository.save(feedback);

        FeedbackCreateDTO feedbackCreateDTO = new FeedbackCreateDTO();
        feedbackCreateDTO.setApplicationID(applicationId);
        feedbackCreateDTO.setJobOfferID(jobOfferID);
        feedbackCreateDTO.setContent(content);
        return feedbackCreateDTO;
    }

    @Transactional
    @Override
    public void delete(Integer jobOfferID, Integer applicationId) {
        feedbackRepository.removeFeedback(jobOfferID, applicationId);
    }
}
