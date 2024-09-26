package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.JobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;

import java.util.List;

@RestController
@RequestMapping("admin/joboffer")
@RequiredArgsConstructor
public class AdminJobOfferController {
    private final AdminJobOfferService adminJobOfferService;


    @PostMapping
    public ResponseEntity<JobOfferDetailsDTO> createJobOffer(@Valid @RequestBody JobOfferCreateDTO jobOfferCreateDTO) {
        JobOfferDetailsDTO createdJobOffer = adminJobOfferService.createJobOffer(jobOfferCreateDTO);
        // Buscar el Ofertante y el JobModality por ID
        return new ResponseEntity<>(createdJobOffer, HttpStatus.CREATED);

    }




    @GetMapping
    public ResponseEntity<List<JobOfferDetailsDTO>> getAllJobOffers() {
        List<JobOfferDetailsDTO> jobOffers = adminJobOfferService.getAllJobOffers();
        return new ResponseEntity<>(jobOffers, HttpStatus.OK);
    }


    @GetMapping("/{jobOfferId}")
    public ResponseEntity<JobOfferDetailsDTO> getJobOfferById(@PathVariable Integer jobOfferId) {
        JobOfferDetailsDTO jobOffer = adminJobOfferService.getJobOfferById(jobOfferId);
        return ResponseEntity.ok(jobOffer);

    }

    @GetMapping("/reputation/{jobOfferId}")
    public ResponseEntity<Reputation> getReputationJobOfferById(@PathVariable Integer jobOfferId) {
        Reputation reputation = adminJobOfferService.getReputationbyJobOfferId(jobOfferId);
        return new ResponseEntity<>(reputation,HttpStatus.OK);
}
    @PutMapping("/{id}")
    public ResponseEntity<JobOfferDetailsDTO> updateJobOffer(@PathVariable Integer id,
                                                             @Valid @RequestBody JobOfferCreateDTO jobOfferCreateDTO) {
        JobOfferDetailsDTO updatedJobOffer = adminJobOfferService.updateJobOffer(id, jobOfferCreateDTO);
        return new ResponseEntity<>(updatedJobOffer, HttpStatus.OK);
    }


}