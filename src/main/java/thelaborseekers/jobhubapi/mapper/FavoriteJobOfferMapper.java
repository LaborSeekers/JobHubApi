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

        FavoriteJobOfferDetailDTO favoriteJobOfferDetailDTO = modelMapper.map(favoriteJobOffers, FavoriteJobOfferDetailDTO.class);

        favoriteJobOfferDetailDTO.setPostulante(favoriteJobOffers.getPostulante().getFirstName() + " " + favoriteJobOffers.getPostulante().getLastName());
        favoriteJobOfferDetailDTO.setJobTitle(favoriteJobOffers.getJobOffer().getTitle());

        return favoriteJobOfferDetailDTO;
    }

    public FavoriteJobOffers toEntity(FavoriteJobOfferCreateDTO favoriteJobOfferCreateDTO) {
        return modelMapper.map(favoriteJobOfferCreateDTO, FavoriteJobOffers.class);
    }

    public FavoriteJobOfferCreateDTO toCreateDTO(FavoriteJobOffers favoriteJobOffers) {
        return modelMapper.map(favoriteJobOffers, FavoriteJobOfferCreateDTO.class);
    }
}
