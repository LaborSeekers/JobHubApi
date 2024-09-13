package thelaborseekers.jobhubapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;

import java.util.List;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    public List<JobOffer> filterJobOffers(String location, Integer modality_id) {
        return jobOfferRepository.findByLocationAndModality(location, modality_id);
    }
}

