package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;

import java.util.List;

@RestController
@RequestMapping("/joboffer")
@RequiredArgsConstructor
public class AdminJobOfferController {
    private final AdminJobOfferService adminJobOfferService;


    @PostMapping
    public ResponseEntity<JobOffer> createJobOffer(@RequestBody JobOffer jobOffer) {
        JobOffer createdJobOffer = adminJobOfferService.createJobOffer(jobOffer);
        // Buscar el Ofertante y el JobModality por ID


        return new ResponseEntity<>(createdJobOffer, HttpStatus.CREATED);

    }




    @GetMapping
    public ResponseEntity<List<JobOffer>> getAllJobOffers() {
        List<JobOffer> jobOffers = adminJobOfferService.getAllJobOffers();
        return new ResponseEntity<List<JobOffer>>(jobOffers, HttpStatus.OK);
    }


    @GetMapping("/{jobOfferId}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable Integer jobOfferId) {
        JobOffer jobOffer = adminJobOfferService.getJobOfferById(jobOfferId);
        return ResponseEntity.ok(jobOffer);

    }

    @GetMapping("/reputation/{jobOfferId}")
    public ResponseEntity<Reputation> getReputationJobOfferById(@PathVariable Integer jobOfferId) {
        Reputation reputation = adminJobOfferService.getReputationbyJobOfferId(jobOfferId);
        return new ResponseEntity<>(reputation,HttpStatus.OK);
}
}