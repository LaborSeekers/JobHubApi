package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.ApplicationCreateDTO;
import thelaborseekers.jobhubapi.dto.ApplicationDetailDTO;
import thelaborseekers.jobhubapi.model.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {
    List<ApplicationDetailDTO> getApplicationsFromJob(Integer jobId);
    Page<ApplicationDetailDTO> getApplicationsFromJob(Integer jobId, Pageable pageable);
    List<ApplicationDetailDTO> getApplicationsFromPost(Integer postId);
    Page<ApplicationDetailDTO> getApplicationsFromPost(Integer postId, Pageable pageable);
    ApplicationDetailDTO create(ApplicationCreateDTO applicationCreateDTO);
    ApplicationDetailDTO update(Integer jobOfferId, Integer postulanteId, ApplicationStatus status);
    void delete(Integer jobOfferId, Integer postulanteId);
}
