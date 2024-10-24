package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferDetailDTO;
import thelaborseekers.jobhubapi.dto.JobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.dto.JobOfferFilterRequestDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.JobOfferMapper;
import thelaborseekers.jobhubapi.mapper.OfertanteMapper;
import thelaborseekers.jobhubapi.mapper.PostulanteMapper;
import thelaborseekers.jobhubapi.mapper.PostulacionMapper;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.entity.Postulacion;
import thelaborseekers.jobhubapi.model.entity.Review;
import thelaborseekers.jobhubapi.model.enums.JobStatus;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.repository.FavoriteJobOffersRepository;
import thelaborseekers.jobhubapi.repository.PostulacionRepository;
import thelaborseekers.jobhubapi.dto.PostulanteProfileDTO;
import thelaborseekers.jobhubapi.repository.JobModalityRepository;
import thelaborseekers.jobhubapi.repository.JobOfferFilterRequestRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.service.AdminFavoriteJobOffersService;
import thelaborseekers.jobhubapi.service.AdminJobOfferService;
import thelaborseekers.jobhubapi.service.AdminOfertanteService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

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

    @Autowired
    private JobOfferFilterRequestRepository jobOfferFilterRequestRepository;

    @Override
    @Transactional(readOnly = true)
    public JobOfferDetailsDTO getJobOfferById(Integer jobOfferId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ResourceNotFoundException("JobOffer not found with id: " + jobOfferId));

        return jobOfferMapper.toJobOfferDetailsDTO(jobOffer);
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


        /*
    @Override
    public List<JobOfferFilterRequestDTO> filterJobOffer(JobOfferFilterRequestDTO filterRequest) {

        List<JobOffer> jobOffers = jobOfferFilterRequestRepository.findByLocationAndTitle(filterRequest.getLocation(), filterRequest.getTitle());

        return jobOffers.stream()
                .map(jobOfferMapper::toDTO)
                .collect(Collectors.toList());
    }
    */

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
        Map<JobOffer, Double> scores = new HashMap<>();

        for (FavoriteJobOffers favoriteJobOffers : favorites) {
            JobOffer favoritejobOffer = favoriteJobOffers.getJobOffer();

            List<JobOffer> similarJobs = jobOfferRepository.findByLocation(favoritejobOffer.getLocation());

            for (JobOffer similarJob : similarJobs) {
                double score = calculateScore(favoritejobOffer, similarJob);
                scores.put(similarJob, scores.getOrDefault(similarJob, 0.0) + score);
            }
        }
        return scores.entrySet().stream()
                .sorted(Map.Entry.<JobOffer, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(5)
                .map(jobOfferMapper::toJobOfferDetailsDTO)
                .toList();
    }

    private double calculateScore(JobOffer favoriteJob, JobOffer similarJob) {
        double score = 0.0;

        if (favoriteJob.getJobModality().equals(similarJob.getJobModality())) {
            score += 1.0;
        }

        if (favoriteJob.getLocation().equalsIgnoreCase(similarJob.getLocation())) {
            score += 2.0;
        }

        double salaryDifference = Math.abs(favoriteJob.getSalary() - similarJob.getSalary());
        if (salaryDifference <= (favoriteJob.getSalary() * 0.1)) {
            score += 1.0;
        } else if (salaryDifference <= (favoriteJob.getSalary() * 0.2)) {
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
}