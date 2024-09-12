package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;
import thelaborseekers.jobhubapi.repository.FavoriteJobOffersRepository;
import thelaborseekers.jobhubapi.service.AdminFavoriteJobOffersService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminFavoriteJobOffersImpl implements AdminFavoriteJobOffersService {
    private final FavoriteJobOffersRepository favoriteJobOffersRepository;

    @Transactional(readOnly = true)
    @Override
    public List<FavoriteJobOffers> get_JobOffers(Integer postulante_id) {
        return favoriteJobOffersRepository.findByPostulanteId(postulante_id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FavoriteJobOffers> get_PostulantsForJobOffer(Integer jobOfferId) {
        return favoriteJobOffersRepository.findByJobOfferId(jobOfferId);
    }

    @Transactional
    @Override
    public void create(Integer jobOfferId, Integer postulanteId) {
        favoriteJobOffersRepository.addFavoriteJobOffer(jobOfferId, postulanteId);
    }

    @Override
    public void delete(Integer jobOfferId, Integer postulanteId) {
        favoriteJobOffersRepository.deleteFavoriteJobOffers(jobOfferId, postulanteId);
    }
}
