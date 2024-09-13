package thelaborseekers.jobhubapi.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
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

    @Override
    @Transactional
    public Review createReview(Review review) {
        //Asigancion Id Foreign Keys
        //Ofertante ofertante = ofertanteRepository.findById(jobOffer.getOfertante().getId())
          //      .orElseThrow(()->new RuntimeException("Ofertante not found with id: "+jobOffer.getOfertante().getId()));
        //jobOffer.setOfertante(ofertante);
        Postulante postulante = postulanteRepository.findById(review.getPostulante().getId()).orElse(null);
        review.setPostulante(postulante);

        JobOffer jobOffer = jobOfferRepository.findById(review.getJobOffer().getId()).orElse(null);
        review.setJobOffer(jobOffer);

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> getAllReviewsByJobOfferId(int jobOfferId) {
        return reviewRepository.findByJobOfferId(jobOfferId);
    }
}
