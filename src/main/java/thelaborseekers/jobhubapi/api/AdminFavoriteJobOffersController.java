package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferDetailDTO;
import thelaborseekers.jobhubapi.service.AdminFavoriteJobOffersService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/fav-job-offers")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE')")
public class AdminFavoriteJobOffersController {
    private final AdminFavoriteJobOffersService adminFavoriteJobOffersService;

    @GetMapping("/{job_offer_id}/postulants")
    public ResponseEntity<List<FavoriteJobOfferDetailDTO>> listPostulantes(@PathVariable int job_offer_id) {
        List<FavoriteJobOfferDetailDTO> lista = adminFavoriteJobOffersService.get_PostulantsForJobOffer(job_offer_id);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/postulants/{postulant_id}")
    public ResponseEntity<List<FavoriteJobOfferDetailDTO>> listFavoriteJobOffers(@PathVariable int postulant_id) {
        List<FavoriteJobOfferDetailDTO> lista = adminFavoriteJobOffersService.get_JobOffers(postulant_id);
        return ResponseEntity.ok(lista);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/postulants/{postulant_id}/job-offers/{job_offer_id}")
    public ResponseEntity<Void> deleteFavoriteJobOffer(@PathVariable int job_offer_id,@PathVariable int postulant_id) {
        adminFavoriteJobOffersService.delete(job_offer_id, postulant_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/postulants/{postulant_id}/job-offers")
    public ResponseEntity<FavoriteJobOfferCreateDTO> addFavoriteJobOffer(@RequestParam int job_offer_id, @PathVariable int postulant_id) {
        FavoriteJobOfferCreateDTO fjo = adminFavoriteJobOffersService.create(job_offer_id, postulant_id);
        return new ResponseEntity<>(fjo, HttpStatus.CREATED);
    }
}
