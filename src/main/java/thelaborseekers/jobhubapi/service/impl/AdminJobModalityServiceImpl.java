package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.repository.JobModalityRepository;
import thelaborseekers.jobhubapi.service.AdminJobModalityService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminJobModalityServiceImpl implements AdminJobModalityService {
    private final JobModalityRepository jobModalityRepository;

    @Transactional(readOnly = true)
    @Override
    public List<JobModality> findAll() {
        return jobModalityRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Page<JobModality> paginate(Pageable pageable) {
        return jobModalityRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public JobModality create(JobModality postulante) {
        return jobModalityRepository.save(postulante);
    }

    @Transactional(readOnly = true)
    @Override
    public JobModality findById(Integer id) {
        return jobModalityRepository.findById(id).orElseThrow(()->new RuntimeException("Empresa not found with id:" + id));
    }

    @Transactional
    @Override
    public JobModality update(Integer id, JobModality updatedPostulante) {
        JobModality postulanteFromDB = findById(id);
        postulanteFromDB.setName(updatedPostulante.getName());
        postulanteFromDB.setDescription(updatedPostulante.getDescription());
        return jobModalityRepository.save(postulanteFromDB);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        JobModality postulante = jobModalityRepository.findById(id).orElseThrow(()->new RuntimeException("JobModality not found with id:" + id));
        jobModalityRepository.delete(postulante);
    }
}
