package thelaborseekers.jobhubapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.service.JobOfferService;

import java.util.List;

@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping("/api/job-offers")
    public List<JobOffer> getJobOffers(@RequestParam(required = false) String location,
                                       @RequestParam(required = false) Integer modality_id) {
        return jobOfferService.filterJobOffers(location, modality_id);
    }
}
