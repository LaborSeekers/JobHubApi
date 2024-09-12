package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;
import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;

public interface AdminFavoriteJobOffersService {
    List<FavoriteJobOffers> get_JobOffers(Integer postulante_id);
    List<FavoriteJobOffers> get_PostulantsForJobOffer(Integer jobOfferId);
    void create(Integer jobOfferId, Integer postulanteId);
    void delete(Integer jobOfferId, Integer postulanteId);
}
