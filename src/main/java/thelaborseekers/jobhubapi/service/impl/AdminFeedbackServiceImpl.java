package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.FeedbackCreateDTO;
import thelaborseekers.jobhubapi.dto.FeedbackDetailDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.FeedbackMapper;
import thelaborseekers.jobhubapi.model.entity.Application;
import thelaborseekers.jobhubapi.model.entity.Feedback;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.repository.ApplicationRepository;
import thelaborseekers.jobhubapi.repository.FeedbackRepository;
import thelaborseekers.jobhubapi.service.AdminFeedbackService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminFeedbackServiceImpl implements AdminFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final ApplicationRepository applicationRepository;
    private final SimpMessagingTemplate messagingTemplate;

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

    @Override
    public List<FeedbackDetailDTO> getFeedbacksFromPostulante(Integer postulanteId) {
        List<Feedback> list = feedbackRepository.getFeedbackFromPostulante(postulanteId);
        return list.stream()
                .map(feedbackMapper::toDetailDTO)
                .toList();
    }

    @Transactional
    @Override
    public FeedbackDetailDTO create(Integer applicationId, String content) {
        Application app = applicationRepository.findById(applicationId).orElseThrow(
                () -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        Feedback feedback = feedbackRepository.getFeedbacksByJobOfferAndApplication(app.getJobOffer().getId(), applicationId);
        if (feedback != null) {
            throw new BadRequestException("Ya existe feedback con esos id");
        }



        JobOffer j = app.getJobOffer();
        feedback = new Feedback();
        feedback.setApplication(app);
        feedback.setDateCreated(LocalDateTime.now());
        feedback.setJobOffer(j);
        feedback.setContent(content);
        feedback = feedbackRepository.save(feedback);

        FeedbackDetailDTO feedbackDetailDTO = feedbackMapper.toDetailDTO(feedback);

        String userId = "POSTULANTE" + app.getPostulante().getId();
        String userDestination = "/user/" + userId + "/notifications";
        messagingTemplate.convertAndSend(userDestination, feedbackDetailDTO);

        return feedbackDetailDTO;
    }

    @Transactional
    @Override
    public FeedbackCreateDTO update(Integer applicationId, String content) {
        Application app = applicationRepository.findById(applicationId).orElseThrow(
                () -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        Integer jobOfferID = app.getJobOffer().getId();

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
