package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.model.entity.JobModality;

import java.util.List;

public interface AdminJobModalityService {
    List<JobModality> findAll();
    Page<JobModality> paginate(Pageable pageable);
    JobModality create(JobModality postulante);
    JobModality findById(Integer id);
    JobModality update(Integer id, JobModality updatedPostulante);
    void delete(Integer id);
}
