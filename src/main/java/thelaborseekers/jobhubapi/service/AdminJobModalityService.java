package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.JobModalityDTO;

import java.util.List;

public interface AdminJobModalityService {
    List<JobModalityDTO> findAll();
    Page<JobModalityDTO> paginate(Pageable pageable);
    JobModalityDTO create(JobModalityDTO postulante);
    JobModalityDTO findById(Integer id);
    JobModalityDTO update(Integer id, JobModalityDTO updatedPostulante);
    void delete(Integer id);
}
