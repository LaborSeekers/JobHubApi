package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.ReviewCreateDTO;
import thelaborseekers.jobhubapi.dto.ReviewDetailsDTO;
import thelaborseekers.jobhubapi.model.entity.Review;

@Component
public class ReviewMapper {

    private final ModelMapper modelMapper;

    public ReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // Convierte la entidad Review a ReviewDetailsDTO
    public ReviewDetailsDTO toReviewDetailsDTO(Review review) {
        ReviewDetailsDTO dto = modelMapper.map(review, ReviewDetailsDTO.class);
        dto.setPostulanteName(review.getPostulante().getFirstName()+" " +review.getPostulante().getLastName());
        dto.setJobOfferTitle(review.getJobOffer().getTitle());
        return dto;
    }

    // Convierte ReviewCreateDTO a la entidad Review
    public Review toReview(ReviewCreateDTO reviewCreateDTO) {
        return modelMapper.map(reviewCreateDTO, Review.class);
    }

    // Convierte la entidad Review a ReviewCreateDTO (opcional)
    public ReviewCreateDTO toReviewCreateDTO(Review review) {
        return modelMapper.map(review, ReviewCreateDTO.class);
    }
}
