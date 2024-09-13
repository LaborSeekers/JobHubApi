package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Application;
import thelaborseekers.jobhubapi.model.entity.Feedback;
import thelaborseekers.jobhubapi.service.AdminFeedbackService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feedback")
public class AdminFeedbackController {
    private final AdminFeedbackService adminFeedbackService;

    @GetMapping("/job/{job_offer_id}/application")
    public ResponseEntity<Feedback> getFeedbackByID(@PathVariable Integer job_offer_id, @RequestParam Integer applicationId) {
        Feedback feedback = adminFeedbackService.getFeedbacksByJobOfferAndApplication(job_offer_id, applicationId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @GetMapping("/by-job-offer")
    public ResponseEntity<List<Feedback>> getFeedbacksByJobOffer(@RequestParam Integer jobOfferId) {
        return ResponseEntity.ok(adminFeedbackService.getFeedbacksByJobOffer(jobOfferId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/job/{job_offer_id}/add-feedback")
    public ResponseEntity<Void> create(@PathVariable Integer job_offer_id, @RequestParam Integer applicationId, @RequestParam String content) {
        adminFeedbackService.create(job_offer_id, applicationId, content);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> update(@RequestParam Integer jobOfferID, @RequestParam Integer applicationId, @RequestParam String content) {
        adminFeedbackService.update(jobOfferID, applicationId, content);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer jobOfferID, @RequestParam Integer applicationId) {
        adminFeedbackService.delete(jobOfferID, applicationId);
        return ResponseEntity.noContent().build();
    }
}
