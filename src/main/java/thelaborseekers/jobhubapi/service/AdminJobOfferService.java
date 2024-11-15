package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.*;
import thelaborseekers.jobhubapi.model.enums.JobStatus;
import thelaborseekers.jobhubapi.model.enums.Reputation;

import java.util.List;

public interface AdminJobOfferService {
// CRUD
    JobOfferDetailsDTO createJobOffer(JobOfferCreateDTO jobOfferCreateDTO);

    List<JobOfferDetailsDTO> getAllJobOffers();


    Page<JobOfferDetailsDTO> getJobOffersPage (String location, Integer modality, JobStatus status, String title, Pageable pageable);

    List<JobOfferDetailsDTO> findAllActive();

    //Obtener lista de ofertas por empresa

    List<JobOfferDetailsDTO> getJobOffersByCompanyId(Integer companyId);

    JobOfferDetailsDTO getJobOfferById(Integer jobOfferId);
    Page<JobOfferDetailsDTO> getJobOffersByIds(List<Integer> jobOfferIds,String location, Integer modality, JobStatus status, String title, Pageable pageable);
    Page<JobOfferDetailsDTO> getJobOffersByOffertanteId(Integer offertanteId, String location, Integer modality, JobStatus status, String title, Pageable pageable);

    JobOfferDetailsDTO  updateJobOffer(Integer jobOfferId,JobOfferCreateDTO  jobOfferCreateDTO);

    void deleteJobOffer(Integer jobOfferId);

    Reputation getReputationbyJobOfferId(Integer jobOfferId);

    JobOfferDetailsDTO findById(Integer jobOfferId);

    //List<JobOfferFilterRequestDTO> filterJobOffer(JobOfferFilterRequestDTO filterRequestDTO);

    List<JobOfferFilterRequestDTO> filterJobOffer(String location, String title);

    List<JobOfferDetailsDTO> getRecommendations(Integer postulanteId);

    List<PostulanteProfileDTO> getPostulantesByJobOfferId(Integer jobOfferId);

    
    JobOfferDetailsDTO updateJobOfferStatus(Integer jobOfferId, JobStatus status);

    List<JobOfferAplicantsDTO> getJobOffersWithApplicantsCountByOfertanteId(Integer ofertanteId);
}
