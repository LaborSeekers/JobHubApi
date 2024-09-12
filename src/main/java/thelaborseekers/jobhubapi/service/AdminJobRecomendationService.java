package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.JobOffer;

import java.util.List;

public interface AdminJobRecomendationService {
    public List<JobOffer> getRecommendedJobs(Integer postulanteId);
}
