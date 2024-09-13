package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.Review;

import java.util.List;

public interface AdminReviewService {
    Review createReview(Review review);
    List<Review> getAllReviewsByJobOfferId(int jobOfferId);
}
