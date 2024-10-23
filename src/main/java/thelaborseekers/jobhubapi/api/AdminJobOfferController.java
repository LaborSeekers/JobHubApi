package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.JobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.dto.JobOfferFilterRequestDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;

import java.util.List;

@RestController
@RequestMapping("admin/joboffer")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
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

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @GetMapping("/{jobOfferId}")
    public ResponseEntity<JobOfferDetailsDTO> getJobOfferById(@PathVariable Integer jobOfferId) {
        JobOfferDetailsDTO jobOffer = adminJobOfferService.getJobOfferById(jobOfferId);
        return ResponseEntity.ok(jobOffer);

    }
    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteJobOffer(@PathVariable Integer id) {
        adminJobOfferService.deleteJobOffer(id);
    }

    /*
    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @PostMapping("/ofertas/filter")
    public ResponseEntity<List<JobOfferFilterRequestDTO>> filterJobOffers(@RequestBody JobOfferFilterRequestDTO jobOfferFilterRequestDTO) {
        List<JobOfferFilterRequestDTO> jobOffers = adminJobOfferService.filterJobOffer(jobOfferFilterRequestDTO);

        if(jobOffers.isEmpty())
        {
            throw new BadRequestException("No se encontraron ofertas con los filtros aplicados. ");
        }
        return new ResponseEntity<>(jobOffers, HttpStatus.OK);
    }
     */

    @PostMapping("/ofertas/filter")
    public ResponseEntity<List<JobOfferFilterRequestDTO>> filterJobOffer(@RequestParam(required = false, defaultValue = "") String location, @RequestParam(required = false, defaultValue = "") String title){
        List<JobOfferFilterRequestDTO> jobOffers = adminJobOfferService.filterJobOffer(location, title);

        if(jobOffers.isEmpty()){
            throw new BadRequestException("No se encontraron ofertas con los filtros aplicados. ");
        }

        return new ResponseEntity<>(jobOffers, HttpStatus.OK);
    }
}