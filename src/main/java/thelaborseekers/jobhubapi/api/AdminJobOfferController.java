package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
public class AdminJobOfferController {
    private final AdminJobOfferService adminJobOfferService;


    @PostMapping
    public ResponseEntity<JobOfferDetailsDTO> createJobOffer(@Valid @RequestBody JobOfferCreateDTO jobOfferCreateDTO) {
        JobOfferDetailsDTO createdJobOffer = adminJobOfferService.createJobOffer(jobOfferCreateDTO);
        // Buscar el Ofertante y el JobModality por ID
        return new ResponseEntity<>(createdJobOffer, HttpStatus.CREATED);

    }
    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
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



    // Endpoint para obtener ofertas de trabajo por ID de empresa
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobOfferDetailsDTO>> getJobOffersByCompanyId(@PathVariable Integer companyId) {
        List<JobOfferDetailsDTO> jobOffers = adminJobOfferService.getJobOffersByCompanyId(companyId);
        return ResponseEntity.ok(jobOffers); // Devuelve una respuesta con las ofertas encontradas
    }
}