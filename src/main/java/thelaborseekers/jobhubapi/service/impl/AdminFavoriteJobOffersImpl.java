package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferDetailDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.mapper.FavoriteJobOfferMapper;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.repository.FavoriteJobOffersRepository;
import thelaborseekers.jobhubapi.repository.JobOfferRepository;
import thelaborseekers.jobhubapi.repository.PostulanteRepository;
import thelaborseekers.jobhubapi.service.AdminFavoriteJobOffersService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminFavoriteJobOffersImpl implements AdminFavoriteJobOffersService {
    private final FavoriteJobOffersRepository favoriteJobOffersRepository;
    private final FavoriteJobOfferMapper favoriteJobOfferMapper;
    private final JobOfferRepository jobOfferRepository;
    private final PostulanteRepository postulanteRepository;

    @Transactional(readOnly = true)
    @Override
    public List<FavoriteJobOfferDetailDTO> get_JobOffers(Integer postulante_id) {
        List<FavoriteJobOffers> fjo = favoriteJobOffersRepository.findByPostulanteId(postulante_id);
        return fjo.stream()
                .map(favoriteJobOfferMapper::toDetailDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<FavoriteJobOfferDetailDTO> get_PostulantsForJobOffer(Integer jobOfferId) {
        List<FavoriteJobOffers> fjo = favoriteJobOffersRepository.findByPostulanteId(jobOfferId);
        return fjo.stream()
                .map(favoriteJobOfferMapper::toDetailDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FavoriteJobOfferDetailDTO> get_PostulantsForJobOffer(Integer jobOfferId, Pageable pageable) {
        Page<FavoriteJobOffers> fjo = favoriteJobOffersRepository.findByPostulanteId(jobOfferId, pageable);
        return fjo.map(favoriteJobOfferMapper::toDetailDTO);
    }

    @Transactional
    @Override
    public FavoriteJobOfferCreateDTO create(Integer jobOfferId, Integer postulanteId) {
        favoriteJobOffersRepository.findByJobOfferIdAndPostulanteId(jobOfferId, postulanteId)
                .ifPresent(fjo -> {
                    throw new BadRequestException("Ya existe una oferta de trabajo favorita con esos id");
                });

        postulanteRepository.findById(postulanteId).orElseThrow(
                () -> new ResourceNotFoundException("Postulante not found with id: " + postulanteId));
        jobOfferRepository.findById(jobOfferId).orElseThrow(
                () -> new ResourceNotFoundException("Job offer not found with id: " + jobOfferId));

        favoriteJobOffersRepository.addFavoriteJobOffer(jobOfferId, postulanteId);

        FavoriteJobOfferCreateDTO favoriteJobOfferCreateDTO = new FavoriteJobOfferCreateDTO();
        favoriteJobOfferCreateDTO.setJobOfferID(jobOfferId);
        favoriteJobOfferCreateDTO.setPostulanteID(postulanteId);

        return favoriteJobOfferCreateDTO;
    }

    @Transactional
    @Override
    public void delete(Integer jobOfferId, Integer postulanteId) {
        favoriteJobOffersRepository.deleteFavoriteJobOffers(jobOfferId, postulanteId);
    }
}
