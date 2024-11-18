package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.ReviewCreateDTO;
import thelaborseekers.jobhubapi.dto.ReviewDetailsDTO;

import java.util.List;

public interface AdminReviewService {
    ReviewDetailsDTO createReview(ReviewCreateDTO reviewCreateDTO);
    List<ReviewDetailsDTO> getAllReviewsByJobOfferId(int jobOfferId);
}
