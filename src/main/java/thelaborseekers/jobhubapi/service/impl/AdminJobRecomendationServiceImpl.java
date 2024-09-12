package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.repository.FavoriteJobOffersRepository;
import thelaborseekers.jobhubapi.repository.JobRecomendationsRepository;
import thelaborseekers.jobhubapi.service.AdminJobRecomendationService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AdminJobRecomendationServiceImpl implements AdminJobRecomendationService {
    private final JobRecomendationsRepository jobRecomendationsRepository;
    private final FavoriteJobOffersRepository favoriteJobOffersRepository;

    @Override
    public List<JobOffer> getRecommendedJobs(Integer postulanteId) {
        List<JobOffer> preferredJobs = favoriteJobOffersRepository.findByPostulanteId(postulanteId);
        Set<JobOffer> recommendedJobs = new HashSet<>();

        for (JobOffer favorite : preferredJobs) {
            long salary = favorite.getSalary();

            List<JobOffer> similarJobs = jobRecomendationsRepository.findSimilarJobs(salary);
            System.out.println(similarJobs);
            recommendedJobs.addAll(similarJobs);
        }

        recommendedJobs.removeAll(preferredJobs);
        return new ArrayList<>(recommendedJobs);
    }

}
