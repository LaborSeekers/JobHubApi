package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.*;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.JobOfferMapper;
import thelaborseekers.jobhubapi.mapper.PostulanteMapper;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.model.enums.JobStatus;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.repository.FavoriteJobOffersRepository;
import thelaborseekers.jobhubapi.repository.PostulacionRepository;
import thelaborseekers.jobhubapi.repository.JobModalityRepository;
import thelaborseekers.jobhubapi.repository.JobOfferFilterRequestRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminJobOfferServiceImpl implements AdminJobOfferService{

    private final JobOfferRepository jobOfferRepository;
    private final AdminOfertanteService adminOfertanteService;
    private final OfertanteRepository ofertanteRepository;
    private final PostulacionRepository postulacionRepository;
    private final JobModalityRepository jobModalityRepository;
    private final JobOfferMapper jobOfferMapper;
    private final PostulanteMapper postulanteMapper;
    private final FavoriteJobOffersRepository favoriteJobOffersRepository;
    private final JobOfferFilterRequestRepository jobOfferFilterRequestRepository;

    @Override
    @Transactional(readOnly = true)
    public JobOfferDetailsDTO getJobOfferById(Integer jobOfferId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ResourceNotFoundException("JobOffer not found with id: " + jobOfferId));

        return jobOfferMapper.toJobOfferDetailsDTO(jobOffer);
    }

    @Override
    public Page<JobOfferDetailsDTO> getJobOffersByIds(List<Integer> jobOfferIds, String location, Integer modality, JobStatus status, String title, Pageable pageable) {
        String locationFixed = '%' + location + '%';
        String titleFixed = '%' + title + '%';
        Page<JobOffer> page = jobOfferRepository.findByIds(jobOfferIds, locationFixed, modality, status, titleFixed, pageable);
        return page.map(jobOfferMapper::toJobOfferDetailsDTO);
    }

    @Override
    @Transactional
    public JobOfferDetailsDTO createJobOffer(JobOfferCreateDTO jobOfferCreateDTO) {

        jobOfferRepository.findByTitle(jobOfferCreateDTO.getTitle()).ifPresent(jobOffer ->
        {throw new BadRequestException("Ya existe un trabajo con el mismo titulo.");});


        //Asigancion Id Foreign Keys
        Ofertante ofertante = ofertanteRepository.findById(jobOfferCreateDTO.getOfertante_id()).orElseThrow(()->
                new ResourceNotFoundException("Ofertante not found with id: " + jobOfferCreateDTO.getOfertante_id()));

        JobModality jobModality= jobModalityRepository.findById(jobOfferCreateDTO.getJobModality_id()).orElseThrow(()->
                new ResourceNotFoundException("JobModality not found with id: " + jobOfferCreateDTO.getJobModality_id()));

        JobOffer jobOffer = jobOfferMapper.toJobOffer(jobOfferCreateDTO);

        jobOffer.setOfertante(ofertante);
        jobOffer.setJobModality(jobModality);
        jobOffer.setCreatedAt(LocalDateTime.now());

        // Verificar y establecer la fecha de publicación y el estado
        if (jobOfferCreateDTO.getScheduledPublishAt() != null) {
            jobOffer.setScheduledPublishAt(jobOfferCreateDTO.getScheduledPublishAt());
            jobOffer.setStatus(JobStatus.INACTIVE);  // Si hay una fecha de programación, el estado es INACTIVE
        } else {
            jobOffer.setStatus(jobOfferCreateDTO.getStatus() != null ? jobOfferCreateDTO.getStatus() : JobStatus.ACTIVE);
        }

        return jobOfferMapper.toJobOfferDetailsDTO(jobOfferRepository.save(jobOffer));
    }

    @Override
    @Transactional (readOnly = true)
    public List<JobOfferDetailsDTO> getAllJobOffers() {
        List<JobOffer> joboffers =jobOfferRepository.findAll();
        return joboffers.stream().map(jobOfferMapper::toJobOfferDetailsDTO).toList();
    }

    @Override
    public Page<JobOfferDetailsDTO> getJobOffersPage(String location, Integer modality, JobStatus status, String title, Pageable pageable) {
        String locationFixed = '%' + location + '%';
        String titleFixed = '%' + title + '%';
        Page<JobOffer> joboffers = jobOfferRepository.findByLocationAndModalityAndStatusAndTitle(locationFixed, modality, status, titleFixed ,pageable);
        return joboffers.map(jobOfferMapper::toJobOfferDetailsDTO);
    }
    @Override
    public Page<JobOfferDetailsDTO> getJobOffersByOffertanteId(Integer offertanteId, String location, Integer modality, JobStatus status, String title, Pageable pageable) {
        // Agregar caracteres comodín a location y title para hacer coincidencias parciales
        String locationFixed = location != null ? '%' + location + '%' : null;
        String titleFixed = title != null ? '%' + title + '%' : null;

        // Llama al repositorio para obtener las ofertas de trabajo según los filtros aplicados
        Page<JobOffer> jobOffers = jobOfferRepository.findByLocationAndModalityAndStatusAndTitleAndOfertanteId(
                locationFixed, modality, status, titleFixed, offertanteId, pageable
        );

        // Mapea las ofertas de trabajo a DTOs
        return jobOffers.map(jobOfferMapper::toJobOfferDetailsDTO);
    }



    @Override
    public List<JobOfferDetailsDTO> findAllActive() {
        List<JobOffer> joboffers =jobOfferRepository.findAllActive(JobStatus.ACTIVE);
        return joboffers.stream().map(jobOfferMapper::toJobOfferDetailsDTO).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<JobOfferDetailsDTO> getJobOffersByCompanyId(Integer companyId) {
        // Buscar todas las reviews por el ID de la empresa
        List<JobOffer> jobOffers = jobOfferRepository.findByOfertanteEmpresaId(companyId);
        return jobOffers.stream()
                .map(jobOfferMapper::toJobOfferDetailsDTO)
                .toList();
    }


    @Override
    @Transactional
    public JobOfferDetailsDTO  updateJobOffer(Integer jobOfferId, JobOfferCreateDTO jobOfferCreateDTO) {
        JobOffer existingJobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferId));

        // Verificar si hay otra oferta con el mismo título, excluyendo la oferta que se está actualizando
        jobOfferRepository.findByTitle(jobOfferCreateDTO.getTitle()).ifPresent(jobOffer -> {
            if (!jobOffer.getId().equals(jobOfferId)) { // Asegurarse de que no sea la misma oferta
                throw new BadRequestException("Ya existe un trabajo con el mismo título.");
            }
        });

        //Asignar FK antes de actualizar
        Ofertante ofertante = ofertanteRepository.findById(jobOfferCreateDTO.getOfertante_id()).orElseThrow(() ->
                new ResourceNotFoundException("Ofertante not found with id: " + jobOfferCreateDTO.getOfertante_id()));

        JobModality jobModality = jobModalityRepository.findById(jobOfferCreateDTO.getJobModality_id()).orElseThrow(() ->
                new ResourceNotFoundException("JobModality not found with id: " + jobOfferCreateDTO.getJobModality_id()));


        // Actualización de los campoos
        existingJobOffer.setTitle(jobOfferCreateDTO.getTitle());
        existingJobOffer.setDescription(jobOfferCreateDTO.getDescription());
        existingJobOffer.setCreatedAt(LocalDateTime.now());
        existingJobOffer.setBenefits(jobOfferCreateDTO.getBenefits());
        existingJobOffer.setRequirements(jobOfferCreateDTO.getRequirements());
        existingJobOffer.setSalary(jobOfferCreateDTO.getSalary());
        existingJobOffer.setLocation(jobOfferCreateDTO.getLocation());
        existingJobOffer.setStatus(jobOfferCreateDTO.getStatus());
        // Actualizar el estado y la fecha de publicación programada
        if (jobOfferCreateDTO.getScheduledPublishAt() != null) {
            existingJobOffer.setStatus(JobStatus.INACTIVE);
            existingJobOffer.setScheduledPublishAt(jobOfferCreateDTO.getScheduledPublishAt());
        } else {
            existingJobOffer.setStatus(jobOfferCreateDTO.getStatus() != null ? jobOfferCreateDTO.getStatus() : existingJobOffer.getStatus());
        }

        existingJobOffer.setJobModality(jobModality);
        existingJobOffer.setOfertante(ofertante);


        return jobOfferMapper.toJobOfferDetailsDTO(jobOfferRepository.save(existingJobOffer));
    }


        @Override
        @Transactional
        public void deleteJobOffer (Integer jobOfferId){
            // Obtén la entidad JobOffer desde el repositorio
            JobOffer existingJobOffer = jobOfferRepository.findById(jobOfferId)
                    .orElseThrow(() -> new ResourceNotFoundException("JobOffer not found with id: " + jobOfferId));

            // Elimina la entidad
            jobOfferRepository.delete(existingJobOffer);
        }


        @Override
        @Transactional(readOnly = true)
        public Reputation getReputationbyJobOfferId (Integer jobOfferId){
            JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                    .orElseThrow(() -> new ResourceNotFoundException("Job offer not found with id:" + jobOfferId));

            Integer ofertanteId = jobOffer.getOfertante().getId();


            return adminOfertanteService.findById(ofertanteId).getReputation();
        }

        @Override
        public JobOfferDetailsDTO findById (Integer jobOfferId){
            JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElseThrow(() ->
                    new ResourceNotFoundException("JobOffer not found with id: " + jobOfferId));
            return jobOfferMapper.toJobOfferDetailsDTO(jobOffer);
        }

    @Override
    public List<JobOfferFilterRequestDTO> filterJobOffer(String location, String title) {
        List<JobOffer> jobOffers;

        if (!location.isEmpty() && !title.isEmpty()) {
            jobOffers = jobOfferFilterRequestRepository.findByLocationAndTitle(location, title);
        } else if (!location.isEmpty()) {
            //Solo ubicacion
            jobOffers = jobOfferFilterRequestRepository.findByLocation(location);
        } else if (!title.isEmpty()) {
            //Solo titulo
            jobOffers = jobOfferFilterRequestRepository.findByTitle(title);
        } else {
            //ningun filtro
            jobOffers = jobOfferFilterRequestRepository.findAll();
        }

        return jobOffers.stream()
                .map(jobOfferMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<JobOfferDetailsDTO> getRecommendations(Integer postulanteId) {
        List<FavoriteJobOffers> favorites = favoriteJobOffersRepository.findByPostulanteId(postulanteId);

        if (favorites.isEmpty()) {
            return jobOfferRepository.findAllActive(JobStatus.ACTIVE).stream()
                    .limit(5) // Limitar a las primeras 5 ofertas
                    .map(jobOfferMapper::toJobOfferDetailsDTO)
                    .toList();
        }

        Set<Integer> favoriteJobIds = favorites.stream()
                .map(favorite -> favorite.getJobOffer().getId())
                .collect(Collectors.toSet());
        Map<Integer, Double> scores = new HashMap<>();
        List<JobOffer> allJobs = jobOfferRepository.findAllActive(JobStatus.ACTIVE);
        List<JobOffer> simJobs = allJobs.stream()
                .filter(job -> !favoriteJobIds.contains(job.getId()))
                .toList();
        for (FavoriteJobOffers favoriteJobOffers : favorites) {
            JobOffer favoriteJob = favoriteJobOffers.getJobOffer();
            for (JobOffer similarJob : simJobs) {
                double score = calculateScore(favoriteJob, similarJob);
                scores.put(similarJob.getId(), scores.getOrDefault(similarJob.getId(), 0.0) + score);
            }
        }

        // Obtener los trabajos recomendados
        return scores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .map(jobOfferRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(jobOfferMapper::toJobOfferDetailsDTO)
                .toList();
    }

    private double calculateScore(JobOffer favoriteJob, JobOffer similarJob) {
        // Almacenar propiedades en variables locales
        JobModality favoriteModality = favoriteJob.getJobModality();
        JobModality similarModality = similarJob.getJobModality();
        String favoriteLocation = favoriteJob.getLocation();
        String similarLocation = similarJob.getLocation();
        double favoriteSalary = favoriteJob.getSalary();
        double salaryDifference = Math.abs(favoriteSalary - similarJob.getSalary());

        double score = 0.0;

        if (favoriteModality == similarModality) {
            score += 1.0;
        }

        if (favoriteLocation.equalsIgnoreCase(similarLocation)) {
            score += 2.0;
        }

        double salaryThreshold1 = favoriteSalary * 0.1;
        double salaryThreshold2 = favoriteSalary * 0.2;

        if (salaryDifference <= salaryThreshold1) {
            score += 1.0;
        } else if (salaryDifference <= salaryThreshold2) {
            score += 0.5;
        }

        return score;
    }

    @Override
public List<PostulanteProfileDTO> getPostulantesByJobOfferId(Integer jobOfferId) {
    JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
        .orElseThrow(() -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferId));
    
    List<Postulacion> postulaciones = postulacionRepository.findByOfertaLaboral(jobOffer);

    return postulaciones.stream()
        .map((Postulacion postulacion) -> postulanteMapper.toProfileDTO(postulacion.getPostulante())) // Cambiado a toProfileDTO
        .collect(Collectors.toList());
}

@Override
public JobOfferDetailsDTO updateJobOfferStatus(Integer jobOfferId, JobStatus status) {
    JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
        .orElseThrow(() -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferId));

    jobOffer.setStatus(status);
    jobOfferRepository.save(jobOffer);

    return jobOfferMapper.toJobOfferDetailsDTO(jobOffer);
}

    @Override
    public List<JobOfferAplicantsDTO> getJobOffersWithApplicantsCountByOfertanteId(Integer ofertanteId) {
        return jobOfferRepository.findJobOffersWithApplicantsCountByOfertanteId(ofertanteId);
    }


}