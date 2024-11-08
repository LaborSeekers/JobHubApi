package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferDetailDTO;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;


import java.util.List;

public interface AdminFavoriteJobOffersService {
    List<FavoriteJobOfferDetailDTO> get_JobOffers(Integer postulante_id);
    List<FavoriteJobOfferDetailDTO> get_PostulantsForJobOffer(Integer jobOfferId);
    Page<FavoriteJobOfferDetailDTO> get_PostulantsForJobOffer(Integer jobOfferId, Pageable pageable);
    FavoriteJobOfferCreateDTO create(Integer jobOfferId, Integer postulanteId);
    void delete(Integer jobOfferId, Integer postulanteId);
}
