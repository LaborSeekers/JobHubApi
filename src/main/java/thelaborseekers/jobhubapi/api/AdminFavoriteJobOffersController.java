package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.service.AdminFavoriteJobOffersService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AdminFavoriteJobOffersController {
    private final AdminFavoriteJobOffersService adminFavoriteJobOffersService;

    @GetMapping("/job-offers/{job_offer_id}/favorited-by")
    public ResponseEntity<List<Postulante>> listPostulantes(@PathVariable int job_offer_id) {
        List<Postulante> lista = adminFavoriteJobOffersService.get_PostulantsForJobOffer(job_offer_id);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/postulant/{postulant_id}/favorite-job-offers")
    public ResponseEntity<List<JobOffer>> listFavoriteJobOffers(@PathVariable int postulant_id) {
        List<JobOffer> lista = adminFavoriteJobOffersService.get_JobOffers(postulant_id);
        return ResponseEntity.ok(lista);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/postulant/{postulant_id}/remove-favorite-job-offer/{job_offer_id}")
    public ResponseEntity<Void> deleteFavoriteJobOffer(@PathVariable int job_offer_id,@PathVariable int postulant_id) {
        adminFavoriteJobOffersService.delete(job_offer_id, postulant_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/postulant/{postulant_id}/add-favorite-job-offer")
    public ResponseEntity<Void> addFavoriteJobOffer(@RequestParam int job_offer_id, @PathVariable int postulant_id) {
        adminFavoriteJobOffersService.create(job_offer_id, postulant_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
