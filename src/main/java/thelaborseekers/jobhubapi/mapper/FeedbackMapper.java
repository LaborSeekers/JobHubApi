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

        feedbackDetailDTO.setName(feedback.getApplication().getPostulante().getName() + " " + feedback.getApplication().getPostulante().getLastName());
        feedbackDetailDTO.setTitle(feedback.getJobOffer().getTitle());

        return feedbackDetailDTO;
    }

    public Feedback toEntity(FeedbackCreateDTO feedbackCreateDTO) {
        return modelMapper.map(feedbackCreateDTO, Feedback.class);
    }

    public FeedbackCreateDTO toCreateDTO(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackCreateDTO.class);
    }
}
