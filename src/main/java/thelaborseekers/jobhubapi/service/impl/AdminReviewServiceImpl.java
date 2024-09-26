package thelaborseekers.jobhubapi.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.ReviewCreateDTO;
import thelaborseekers.jobhubapi.dto.ReviewDetailsDTO;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.ReviewMapper;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.model.entity.Review;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.repository.ReviewRepository;
import thelaborseekers.jobhubapi.service.AdminReviewService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminReviewServiceImpl implements AdminReviewService {
    private final PostulanteRepository postulanteRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewDetailsDTO createReview(ReviewCreateDTO reviewCreateDTO) {
        Postulante postulante = postulanteRepository.findById(reviewCreateDTO.getPostulante_id()).orElseThrow(
                ()->new ResourceNotFoundException("Postulante not found with id: " + reviewCreateDTO.getPostulante_id())
        );

        JobOffer jobOffer = jobOfferRepository.findById(reviewCreateDTO.getJobOffer_id()).orElseThrow(
                ()->new ResourceNotFoundException("JobOffer not found with id: " + reviewCreateDTO.getJobOffer_id())
        );
        Review review = reviewMapper.toReview(reviewCreateDTO);
        review.setPostulante(postulante);
        review.setJobOffer(jobOffer);

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewDetailsDTO(savedReview);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewDetailsDTO> getAllReviewsByJobOfferId(int jobOfferId) {
        // Buscar todas las reviews por el ID de la oferta de trabajo y convertirlas a DTO de detalles
        List<Review> reviews = reviewRepository.findByJobOfferId(jobOfferId);
        return reviews.stream()
                .map(reviewMapper::toReviewDetailsDTO)
                .toList();
    }  }
