package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;

import java.util.List;

@RestController
@RequestMapping("/recomendations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('POSTULANTE')")
public class JobOfferRecomendationController {
    private final AdminJobOfferService adminJobOfferService;

    @GetMapping
    public ResponseEntity<List<JobOfferDetailsDTO>> recommendations(Integer postulanteId) {
        List<JobOfferDetailsDTO> recommended = adminJobOfferService.getRecommendations(postulanteId);
        return new ResponseEntity<>(recommended, HttpStatus.OK);
    }
}
