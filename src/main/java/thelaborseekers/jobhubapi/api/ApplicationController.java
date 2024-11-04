package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.dto.ApplicationCreateDTO;
import thelaborseekers.jobhubapi.dto.ApplicationDetailDTO;
import thelaborseekers.jobhubapi.model.enums.ApplicationStatus;
import thelaborseekers.jobhubapi.service.ApplicationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
@PreAuthorize("hasAnyRole('ADMIN', 'POSTULANTE', 'OFERTANTE')")
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/by-job")
    public ResponseEntity<List<ApplicationDetailDTO>> findFromJob(@RequestParam Integer id) {
        List<ApplicationDetailDTO> list = applicationService.getApplicationsFromJob(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-postulant")
    public ResponseEntity<List<ApplicationDetailDTO>> findFromPostulant(@RequestParam Integer id) {
        List<ApplicationDetailDTO> list = applicationService.getApplicationsFromPost(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<ApplicationDetailDTO> create(@RequestBody ApplicationCreateDTO applicationCreateDTO) {
        ApplicationDetailDTO ApplicationDetailDTO = applicationService.create(applicationCreateDTO);
        return ResponseEntity.ok(ApplicationDetailDTO);
    }

    @PatchMapping("/update")
    public ResponseEntity<ApplicationDetailDTO> update(@RequestParam Integer postId, @RequestParam Integer jobId, @RequestParam ApplicationStatus status) {
        ApplicationDetailDTO applicationDetailDTO = applicationService.update(jobId, postId, status);
        return ResponseEntity.ok(applicationDetailDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer postId, @RequestParam Integer jobId) {
        applicationService.delete(jobId, postId);
        return ResponseEntity.noContent().build();
    }
}
