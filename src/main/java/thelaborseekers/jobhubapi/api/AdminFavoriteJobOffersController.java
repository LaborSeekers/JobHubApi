package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.service.AdminFavoriteJobOffersService;
import thelaborseekers.jobhubapi.service.AdminPostulanteService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AdminFavoriteJobOffersController {
    private final AdminFavoriteJobOffersService adminFavoriteJobOffersService;

    @GetMapping("/fav")
    public ResponseEntity<List<Postulante>> listPostulantes(int job_offer_id) {
        List<FavoriteJobOffers> lista = adminFavoriteJobOffersService.get_PostulantsForJobOffer(job_offer_id);
        List<Postulante> res = new ArrayList<>();
        for(FavoriteJobOffers fj : lista) {
            res.add(fj.getPostulante());
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/fav2")
    public ResponseEntity<List<JobOffer>> listFavoriteJobOffers(int postulant_id) {
        List<FavoriteJobOffers> lista = adminFavoriteJobOffersService.get_JobOffers(postulant_id);
        List<JobOffer> res = new ArrayList<>();
        for(FavoriteJobOffers fj : lista) {
            res.add(fj.getJobOffer());
        }
        return ResponseEntity.ok(res);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFavoriteJobOffer(int job_offer_id, int postulant_id) {
        adminFavoriteJobOffersService.delete(job_offer_id, postulant_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFavoriteJobOffer(int job_offer_id, int postulant_id) {
        adminFavoriteJobOffersService.create(job_offer_id, postulant_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
