package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.JobOfferCreateDTO;
import thelaborseekers.jobhubapi.dto.JobOfferDetailsDTO;
import thelaborseekers.jobhubapi.dto.JobOfferFilterRequestDTO;
import thelaborseekers.jobhubapi.model.entity.JobOffer;

@Component
public class JobOfferMapper {

    private final ModelMapper modelMapper;

    public JobOfferMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public JobOfferFilterRequestDTO toDTO(JobOffer jobOffer) {
        return modelMapper.map(jobOffer, JobOfferFilterRequestDTO.class);
    }

    //Response
    public JobOfferDetailsDTO toJobOfferDetailsDTO(JobOffer jobOffer) {
        JobOfferDetailsDTO dto = modelMapper.map(jobOffer, JobOfferDetailsDTO.class);
        dto.setOfertanteName(jobOffer.getOfertante().getFirstName()+" "+jobOffer.getOfertante().getLastName());
        dto.setJobModalityName(jobOffer.getJobModality().getName());
        dto.setReputation(jobOffer.getOfertante().getReputation());

        return dto;
    }
    public JobOffer toJobOffer(JobOfferCreateDTO jobOfferCreateDTO) {
        return modelMapper.map(jobOfferCreateDTO, JobOffer.class);
    }


}
