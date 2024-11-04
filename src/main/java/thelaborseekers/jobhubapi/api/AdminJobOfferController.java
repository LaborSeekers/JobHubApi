package thelaborseekers.jobhubapi.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.*;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.enums.JobStatus;
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
    @GetMapping("/page")
    public ResponseEntity<Page<JobOfferDetailsDTO>> findPage(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, String location, Integer modality, JobStatus status, String title) {
        Page<JobOfferDetailsDTO> page = adminJobOfferService.getJobOffersPage(location, modality, status, title, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @GetMapping("/{jobOfferId}")
    public ResponseEntity<JobOfferDetailsDTO> getJobOfferById(@PathVariable Integer jobOfferId) {
        JobOfferDetailsDTO jobOffer = adminJobOfferService.getJobOfferById(jobOfferId);
        return ResponseEntity.ok(jobOffer);
    }

    @PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
    @PostMapping("/details")
    public ResponseEntity<Page<JobOfferDetailsDTO>> getJobOfferByIds(@RequestBody List<Integer> Ids,@RequestParam String location, Integer modality, JobStatus status,@RequestParam String title,@PageableDefault(size = 5) Pageable pageable) {
        Page<JobOfferDetailsDTO> page = adminJobOfferService.getJobOffersByIds(Ids,location,modality,status,title,pageable);
        return ResponseEntity.ok(page);
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

  
    @PostMapping("/ofertas/filter")
    public ResponseEntity<List<JobOfferFilterRequestDTO>> filterJobOffer(@RequestParam(required = false, defaultValue = "") String location, @RequestParam(required = false, defaultValue = "") String title){
        List<JobOfferFilterRequestDTO> jobOffers = adminJobOfferService.filterJobOffer(location, title);

        if(jobOffers.isEmpty()){
            throw new BadRequestException("No se encontraron ofertas con los filtros aplicados. ");
        }

        return new ResponseEntity<>(jobOffers, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OFERTANTE')")
@GetMapping("/{jobOfferId}/postulantes")
public ResponseEntity<List<PostulanteProfileDTO>> getPostulantesByJobOfferId(@PathVariable Integer jobOfferId) {
    List<PostulanteProfileDTO> postulantes = adminJobOfferService.getPostulantesByJobOfferId(jobOfferId);
    return new ResponseEntity<>(postulantes, HttpStatus.OK);
}

@PreAuthorize("hasAnyRole('ADMIN', 'OFERTANTE')")
@PatchMapping("/{jobOfferId}/status")
public ResponseEntity<JobOfferDetailsDTO> updateJobOfferStatus(@PathVariable Integer jobOfferId, @RequestBody JobStatus status) {
    JobOfferDetailsDTO updatedJobOffer = adminJobOfferService.updateJobOfferStatus(jobOfferId, status);
    return new ResponseEntity<>(updatedJobOffer, HttpStatus.OK);
}


}