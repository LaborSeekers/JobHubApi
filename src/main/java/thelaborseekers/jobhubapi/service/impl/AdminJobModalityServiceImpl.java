package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.JobModalityDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.JobModalityMapper;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.repository.JobModalityRepository;
import thelaborseekers.jobhubapi.service.AdminJobModalityService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminJobModalityServiceImpl implements AdminJobModalityService {
    private final JobModalityRepository jobModalityRepository;
    private final JobModalityMapper jobModalityMapper;

    @Transactional(readOnly = true)
    @Override
    public List<JobModalityDTO> findAll() {
        List<JobModality> jobModalities = jobModalityRepository.findAll();
        return jobModalities.stream().map(jobModalityMapper::toDTO).toList();
    }
    @Transactional(readOnly = true)
    @Override
    public Page<JobModalityDTO> paginate(Pageable pageable) {
        Page<JobModality> jobModalities = jobModalityRepository.findAll(pageable);
        return jobModalities.map(jobModalityMapper::toDTO);
    }

    @Transactional
    @Override
    public JobModalityDTO create(JobModalityDTO JobmodalityDTO) {
        jobModalityRepository.findByName(JobmodalityDTO.getName())
                .ifPresent(existinJobModality -> {
                    throw new BadRequestException("Job modality name already exists");
                });
        JobModality jobModality = jobModalityMapper.toEntity(JobmodalityDTO);
        jobModality = jobModalityRepository.save(jobModality);
        return jobModalityMapper.toDTO(jobModality);
    }

    @Transactional(readOnly = true)
    @Override
    public JobModalityDTO findById(Integer id) {
        JobModality Jobmodality = jobModalityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Job modality not found with id:" + id));
        return jobModalityMapper.toDTO(Jobmodality);
    }

    @Transactional
    @Override
    public JobModalityDTO update(Integer id, JobModalityDTO updatedJobModDTO) {
        JobModality jobModalityFromDB = jobModalityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Job modality not found with id:" + id));
        jobModalityRepository.findByName(updatedJobModDTO.getName())
                .filter(existinJobModality -> !existinJobModality.getId().equals(id))
                .ifPresent(existinJobModality -> {
                    throw new BadRequestException("Job modality name already exists");
                });

        jobModalityFromDB.setName(updatedJobModDTO.getName());
        jobModalityFromDB.setDescription(updatedJobModDTO.getDescription());
        jobModalityFromDB = jobModalityRepository.save(jobModalityFromDB);
        return jobModalityMapper.toDTO(jobModalityFromDB);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        JobModality jobModality = jobModalityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("JobModality not found with id:" + id));
        jobModalityRepository.delete(jobModality);
    }
}
