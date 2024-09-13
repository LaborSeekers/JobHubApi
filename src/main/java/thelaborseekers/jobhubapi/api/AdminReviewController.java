package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Review;
import thelaborseekers.jobhubapi.repository.ReviewRepository;
import thelaborseekers.jobhubapi.service.AdminReviewService;

import java.util.List;

@RestController
@RequestMapping("/admin/Review")
@RequiredArgsConstructor
public class AdminReviewController {
    private final AdminReviewService adminReviewService;
    private final ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review){
        Review savedReview = adminReviewService.createReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/joboffer/{jobOfferId}")
    public ResponseEntity<List<Review>> getAllReviewsbyJobOffer(@PathVariable int jobOfferId){
        List<Review> reviewList = adminReviewService.getAllReviewsByJobOfferId(jobOfferId);
        return ResponseEntity.ok(reviewList);
    }

}
