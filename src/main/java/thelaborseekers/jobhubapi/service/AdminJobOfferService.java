package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.JobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.dto.JobOfferFilterRequestDTO;
import thelaborseekers.jobhubapi.model.enums.Reputation;

import java.util.List;

public interface AdminJobOfferService {
// CRUD
    JobOfferDetailsDTO createJobOffer(JobOfferCreateDTO jobOfferCreateDTO);

    List<JobOfferDetailsDTO> getAllJobOffers();

    //Obtener lista de ofertas por empresa

    List<JobOfferDetailsDTO> getJobOffersByCompanyId(Integer companyId);

    JobOfferDetailsDTO getJobOfferById(Integer jobOfferId);

    JobOfferDetailsDTO  updateJobOffer(Integer jobOfferId,JobOfferCreateDTO  jobOfferCreateDTO);

    void deleteJobOffer(Integer jobOfferId);

    Reputation getReputationbyJobOfferId(Integer jobOfferId);

    JobOfferDetailsDTO findById(Integer jobOfferId);

    //List<JobOfferFilterRequestDTO> filterJobOffer(JobOfferFilterRequestDTO filterRequestDTO);

    List<JobOfferFilterRequestDTO> filterJobOffer(String location, String title);

    List<JobOfferDetailsDTO> getRecommendations(Integer postulanteId);

}
