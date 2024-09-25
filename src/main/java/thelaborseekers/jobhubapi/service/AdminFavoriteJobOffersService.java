package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.dto.FavoriteJobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferDetailDTO;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;

public interface AdminFavoriteJobOffersService {
    List<FavoriteJobOfferDetailDTO> get_JobOffers(Integer postulante_id);
    List<FavoriteJobOfferDetailDTO> get_PostulantsForJobOffer(Integer jobOfferId);
    FavoriteJobOfferCreateDTO create(Integer jobOfferId, Integer postulanteId);
    void delete(Integer jobOfferId, Integer postulanteId);
}
