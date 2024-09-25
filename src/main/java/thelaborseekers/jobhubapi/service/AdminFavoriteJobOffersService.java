package thelaborseekers.jobhubapi.service;

import thelaborseekers.jobhubapi.model.entity.JobOffer;
import thelaborseekers.jobhubapi.model.entity.Postulante;

import java.util.List;

public interface AdminFavoriteJobOffersService {
    List<JobOffer> get_JobOffers(Integer postulante_id);
    List<Postulante> get_PostulantsForJobOffer(Integer jobOfferId);
    void create(Integer jobOfferId, Integer postulanteId);
    void delete(Integer jobOfferId, Integer postulanteId);
}
