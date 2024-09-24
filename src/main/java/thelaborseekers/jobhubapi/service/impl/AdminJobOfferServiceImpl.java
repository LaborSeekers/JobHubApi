package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.mapper.OfertanteMapper;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.repository.JobModalityRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminJobOfferServiceImpl implements AdminJobOfferService{

    private final JobOfferRepository jobOfferRepository;
    private final AdminOfertanteService adminOfertanteService;
    private final OfertanteRepository ofertanteRepository;
    private final JobModalityRepository jobModalityRepository;


    @Override
    @Transactional(readOnly = true)
    public JobOffer getJobOfferById(Integer jobOfferId) {
        return jobOfferRepository.findById(jobOfferId).orElseThrow(()->new RuntimeException("JobOffer not found"));
    }

    @Override
    @Transactional
    public JobOffer createJobOffer(JobOffer jobOffer) {

        jobOffer.setCreatedAt(LocalDateTime.now());

        //Asigancion Id Foreign Keys
        Ofertante ofertante = ofertanteRepository.findById(jobOffer.getOfertante().getId())
                .orElseThrow(()->new RuntimeException("Ofertante not found with id: "+jobOffer.getOfertante().getId()));
        jobOffer.setOfertante(ofertante);

       // JobModality jobModality = jobModalityRepository.findById(jobOffer.getJobModality().getId())
        //        .orElseThrow(()->new RuntimeException("Ofertante not found with id: "+jobOffer.getJobModality().getId()));
        //jobOffer.setJobModality(jobModality);

        return jobOfferRepository.save(jobOffer);
    }

    @Override
    @Transactional (readOnly = true)
    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }


    @Override
    @Transactional
    public JobOffer updateJobOffer(Integer jobOfferId, JobOffer jobOffer) {
        JobOffer existingJobOffer = getJobOfferById(jobOfferId);

        existingJobOffer.setTitle(jobOffer.getTitle());
        existingJobOffer.setDescription(jobOffer.getDescription());
        existingJobOffer.setCreatedAt(LocalDateTime.now());
        existingJobOffer.setBenefits(jobOffer.getBenefits());
        existingJobOffer.setRequirements(jobOffer.getRequirements());
        existingJobOffer.setStatus(jobOffer.getStatus());

        return jobOfferRepository.save(existingJobOffer);
    }

    @Override
    @Transactional
    public void deleteJobOffer(Integer jobOfferId) {
        JobOffer existingJobOffer = getJobOfferById(jobOfferId);
        jobOfferRepository.delete(existingJobOffer);
    }

    @Override
    @Transactional(readOnly = true)
    public Reputation getReputationbyJobOfferId(Integer jobOfferId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Job offer not found"));

        Integer ofertanteId = jobOffer.getOfertante().getId();


        return adminOfertanteService.findById(ofertanteId).getReputation();
    }

    @Override
    public JobOffer findById(Integer jobOfferId) {
        return jobOfferRepository.findById(jobOfferId).orElseThrow(()->new RuntimeException("JobOffer not found with id: " + jobOfferId));
    }
}
