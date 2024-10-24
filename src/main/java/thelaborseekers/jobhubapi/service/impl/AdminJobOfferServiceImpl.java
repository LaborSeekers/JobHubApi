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
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobModality;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.enums.JobStatus;
import thelaborseekers.jobhubapi.model.enums.Reputation;
import thelaborseekers.jobhubapi.repository.FavoriteJobOffersRepository;
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
    private final JobModalityRepository jobModalityRepository;
    private final JobOfferMapper jobOfferMapper;
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

        // Verificar si se ha programado una fecha de publicación
        if (jobOfferCreateDTO.getScheduledPublishAt() != null) {
            jobOffer.setScheduledPublishAt(jobOfferCreateDTO.getScheduledPublishAt());
            jobOffer.setStatus(JobStatus.INACTIVE);  // Si hay una fecha de programación, el estado es INACTIVE
        } else {
            // Si no se proporciona una fecha de publicación, verificar y establecer el estado de trabajo
            if (jobOfferCreateDTO.getStatus() == null) {
                jobOffer.setStatus(JobStatus.ACTIVE);  // Establece como ACTIVE si no se proporciona un estado
            } else {
                jobOffer.setStatus(jobOfferCreateDTO.getStatus());  // Asigna el estado proporcionado
            }
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
    @Transactional
    public JobOfferDetailsDTO  updateJobOffer(Integer jobOfferId, JobOfferCreateDTO jobOfferCreateDTO) {
        JobOffer existingJobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferId));

        jobOfferRepository.findByTitle(jobOfferCreateDTO.getTitle()).ifPresent(jobOffer ->
        {
            throw new BadRequestException("Ya existe un trabajo con el mismo titulo.");
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
        existingJobOffer.setJobModality(jobModality);
        existingJobOffer.setOfertante(ofertante);

        // Verificar si se actualiza la fecha de publicación programada
        if (jobOfferCreateDTO.getScheduledPublishAt() != null) {
            // Si hay una nueva fecha de publicación programada, establecer el estado en INACTIVE
            existingJobOffer.setStatus(JobStatus.INACTIVE);
            existingJobOffer.setScheduledPublishAt(jobOfferCreateDTO.getScheduledPublishAt());
        } else {
            // Si no se actualiza la fecha de publicación, mantener el estado actual o el proporcionado
            if (jobOfferCreateDTO.getStatus() != null) {
                existingJobOffer.setStatus(jobOfferCreateDTO.getStatus()); // Usar el estado proporcionado
            } else {
                existingJobOffer.setStatus(existingJobOffer.getStatus()); // Mantener el estado actual
            }
        }

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
}