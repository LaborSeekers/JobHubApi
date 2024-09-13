package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.enums.Reputation;

import java.util.List;

public interface AdminJobOfferService {
// CRUD
    JobOffer createJobOffer(JobOffer jobOffer);

    List<JobOffer> getAllJobOffers();

    JobOffer getJobOfferById(Integer jobOfferId);

    JobOffer updateJobOffer(Integer jobOfferId,JobOffer jobOffer);

    void deleteJobOffer(Integer jobOfferId);

    Reputation getReputationbyJobOfferId(Integer jobOfferId);

    JobOffer findById(Integer jobOfferId);
}
