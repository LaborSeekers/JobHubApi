package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.FeedbackCreateDTO;
import thelaborseekers.jobhubapi.dto.FeedbackDetailDTO;
import thelaborseekers.jobhubapi.model.entity.Feedback;

@Component
public class FeedbackMapper {
    private final ModelMapper modelMapper;

    public FeedbackMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public FeedbackDetailDTO toDetailDTO(Feedback feedback) {

        FeedbackDetailDTO feedbackDetailDTO = modelMapper.map(feedback, FeedbackDetailDTO.class);

        feedbackDetailDTO.setApplicationID(feedback.getApplication().getId());
        feedbackDetailDTO.setJobOfferID(feedback.getJobOffer().getId());
        feedbackDetailDTO.setJobTitle(feedback.getJobOffer().getTitle());
        feedbackDetailDTO.setJobLogo(feedback.getJobOffer().getLogo());
        feedbackDetailDTO.setJobSalary(feedback.getJobOffer().getSalary());
        feedbackDetailDTO.setJobLocation(feedback.getJobOffer().getLocation());
        feedbackDetailDTO.setJobModality(feedback.getJobOffer().getJobModality().getName());

        return feedbackDetailDTO;
    }

    public Feedback toEntity(FeedbackCreateDTO feedbackCreateDTO) {
        return modelMapper.map(feedbackCreateDTO, Feedback.class);
    }

    public FeedbackCreateDTO toCreateDTO(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackCreateDTO.class);
    }
}
