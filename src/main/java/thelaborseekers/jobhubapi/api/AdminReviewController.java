package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.ReviewCreateDTO;
import thelaborseekers.jobhubapi.dto.ReviewDetailsDTO;
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
    public ResponseEntity<ReviewDetailsDTO> createReview(@Valid @RequestBody ReviewCreateDTO reviewCreateDTO) {
        ReviewDetailsDTO savedReview = adminReviewService.createReview(reviewCreateDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/joboffer/{jobOfferId}")
    public ResponseEntity<List<ReviewDetailsDTO>> getAllReviewsbyJobOffer(@PathVariable int jobOfferId){
        List<ReviewDetailsDTO> reviewList = adminReviewService.getAllReviewsByJobOfferId(jobOfferId);
        return ResponseEntity.ok(reviewList);
    }

}
