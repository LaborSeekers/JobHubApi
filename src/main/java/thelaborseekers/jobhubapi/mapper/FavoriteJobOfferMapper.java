package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.FavoriteJobOfferDetailDTO;
import thelaborseekers.jobhubapi.model.entity.FavoriteJobOffers;

@Component
public class FavoriteJobOfferMapper {
    private final ModelMapper modelMapper;

    public FavoriteJobOfferMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public FavoriteJobOfferDetailDTO toDetailDTO(FavoriteJobOffers favoriteJobOffers) {

        FavoriteJobOfferDetailDTO favoriteJobOfferDetailDTO = new FavoriteJobOfferDetailDTO();
        favoriteJobOfferDetailDTO.setPostulante_id(favoriteJobOffers.getPostulante().getId());
        favoriteJobOfferDetailDTO.setJobOffer_id(favoriteJobOffers.getJobOffer().getId());

        return favoriteJobOfferDetailDTO;
    }

    public FavoriteJobOffers toEntity(FavoriteJobOfferCreateDTO favoriteJobOfferCreateDTO) {
        return modelMapper.map(favoriteJobOfferCreateDTO, FavoriteJobOffers.class);
    }

    public FavoriteJobOfferCreateDTO toCreateDTO(FavoriteJobOffers favoriteJobOffers) {
        return modelMapper.map(favoriteJobOffers, FavoriteJobOfferCreateDTO.class);
    }
}
