package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.FeedbackCreateDTO;
import thelaborseekers.jobhubapi.dto.FeedbackDetailDTO;
import thelaborseekers.jobhubapi.service.AdminFeedbackService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feedback")
@PreAuthorize("hasAnyRole('ADMIN','POSTULANTE','OFERTANTE')")
public class AdminFeedbackController {
    private final AdminFeedbackService adminFeedbackService;

    @GetMapping("/job/{job_offer_id}/application")
    public ResponseEntity<FeedbackDetailDTO> getFeedbackByID(@PathVariable Integer job_offer_id, @RequestParam Integer applicationId) {
        FeedbackDetailDTO feedback = adminFeedbackService.getFeedbacksByJobOfferAndApplication(job_offer_id, applicationId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @GetMapping("/by-job-offer")
    public ResponseEntity<List<FeedbackDetailDTO>> getFeedbacksByJobOffer(@RequestParam Integer jobOfferId) {
        return ResponseEntity.ok(adminFeedbackService.getFeedbacksByJobOffer(jobOfferId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/job/{job_offer_id}/add-feedback")
    public ResponseEntity<FeedbackCreateDTO> create(@PathVariable Integer job_offer_id, @RequestParam Integer applicationId, @RequestParam String content) {
        FeedbackCreateDTO feedbackCreateDTO = adminFeedbackService.create(job_offer_id, applicationId, content);
        return new ResponseEntity<>(feedbackCreateDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
    @PatchMapping("/update")
    public ResponseEntity<FeedbackCreateDTO> update(@RequestParam Integer jobOfferID, @RequestParam Integer applicationId, @RequestParam String content) {
        FeedbackCreateDTO feedbackCreateDTO = adminFeedbackService.update(jobOfferID, applicationId, content);
        return new ResponseEntity<>(feedbackCreateDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','OFERTANTE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer jobOfferID, @RequestParam Integer applicationId) {
        adminFeedbackService.delete(jobOfferID, applicationId);
        return ResponseEntity.noContent().build();
    }
}
