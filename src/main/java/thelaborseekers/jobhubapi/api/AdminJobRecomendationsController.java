package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.service.AdminJobRecomendationService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AdminJobRecomendationsController {
    private final AdminJobRecomendationService adminJobRecomendationService;

    @GetMapping("/postulant/{postulant_id}/recomended")
    public ResponseEntity<List<JobOffer>> getJobRecomendations(@PathVariable int postulant_id) {
        List<JobOffer> res = adminJobRecomendationService.getRecommendedJobs(postulant_id);
        return ResponseEntity.ok(res);
    }
}
