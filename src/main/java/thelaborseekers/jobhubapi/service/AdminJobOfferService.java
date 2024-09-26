package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.JobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.model.enums.Reputation;

import java.util.List;

public interface AdminJobOfferService {
// CRUD
    JobOfferDetailsDTO createJobOffer(JobOfferCreateDTO jobOfferCreateDTO);

    List<JobOfferDetailsDTO> getAllJobOffers();

    JobOfferDetailsDTO getJobOfferById(Integer jobOfferId);

    JobOfferDetailsDTO  updateJobOffer(Integer jobOfferId,JobOfferCreateDTO  jobOfferCreateDTO);

    void deleteJobOffer(Integer jobOfferId);

    Reputation getReputationbyJobOfferId(Integer jobOfferId);

    JobOfferDetailsDTO findById(Integer jobOfferId);
}
