package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.ApplicationCreateDTO;
import thelaborseekers.jobhubapi.dto.ApplicationDetailDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.ApplicationMapper;
import thelaborseekers.jobhubapi.model.entity.Application;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.model.enums.ApplicationStatus;
import thelaborseekers.jobhubapi.repository.ApplicationRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.service.ApplicationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobOfferRepository jobOfferRepository;
    private final PostulanteRepository postulanteRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationDetailDTO> getApplicationsFromJob(Integer jobId) {
        jobOfferRepository.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException("Job offer not found with id: " + jobId));
        List<Application> list = applicationRepository.findByJobOfferId(jobId);
        return list.stream()
                .map(applicationMapper::toDTO)
                .toList();
    }

    @Override
    public Page<ApplicationDetailDTO> getApplicationsFromJob(Integer jobId, Pageable pageable) {
        jobOfferRepository.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException("Job offer not found with id: " + jobId));

        Page<Application> list = applicationRepository.findByJobOfferId(jobId, pageable);
        return list.map(applicationMapper::toDTO);
    }

    @Override
    public List<ApplicationDetailDTO> getApplicationsFromPost(Integer postId) {
        postulanteRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postulante not found with id: " + postId));
        List<Application> list = applicationRepository.findByPostulanteId(postId);
        return list.stream()
                .map(applicationMapper::toDTO)
                .toList();
    }

    @Override
    public Page<ApplicationDetailDTO> getApplicationsFromPost(Integer postId, Pageable pageable) {
        postulanteRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Postulante not found with id: " + postId));

        Page<Application> list = applicationRepository.findByPostulanteId(postId, pageable);
        return list.map(applicationMapper::toDTO);
    }

    @Override
    public ApplicationDetailDTO create(ApplicationCreateDTO applicationCreateDTO) {
        Integer jobOfferId = applicationCreateDTO.getJobOffer_id();
        Integer postulanteId = applicationCreateDTO.getPostulante_id();

        applicationRepository.findByJobOfferIdAndPostulanteId(jobOfferId, postulanteId)
                .ifPresent(fjo -> {
                    throw new BadRequestException("Ya existe una aplicacion con esos id");
                });

        Postulante postulante = postulanteRepository.findById(postulanteId).orElseThrow(
                () -> new ResourceNotFoundException("Postulante not found with id: " + postulanteId));
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElseThrow(
                () -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferId));

        Application entity = applicationMapper.toEntity(applicationCreateDTO);
        entity.setJobOffer(jobOffer);
        entity.setPostulante(postulante);
        entity.setDateApplied(LocalDateTime.now());
        entity.setDateUpdated(entity.getDateApplied());
        entity.setStatus(ApplicationStatus.PENDING);

        entity = applicationRepository.save(entity);

        return applicationMapper.toDTO(entity);
    }

    @Override
    public ApplicationDetailDTO update(Integer jobOfferId, Integer postulanteId, ApplicationStatus status) {
        Application application = applicationRepository.findByJobOfferIdAndPostulanteId(jobOfferId, postulanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found for job offer id: " + jobOfferId + " and postulante id: " + postulanteId));

        application.setStatus(status);
        application.setDateUpdated(LocalDateTime.now());
        applicationRepository.save(application);

        ApplicationDetailDTO applicationDetailDTO = new ApplicationDetailDTO();
        applicationDetailDTO.setJobOffer_id(jobOfferId);
        applicationDetailDTO.setPostulante_id(postulanteId);
        applicationDetailDTO.setStatus(status);

        return applicationDetailDTO;
    }

    @Override
    public void delete(Integer jobOfferId, Integer postulanteId) {
        applicationRepository.findByJobOfferIdAndPostulanteId(jobOfferId, postulanteId)
                .ifPresent(applicationRepository::delete);
    }
}
