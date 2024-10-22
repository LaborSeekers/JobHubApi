package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.SubscriptionCreateDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionUpdateDTO;
import thelaborseekers.jobhubapi.model.entity.Subscription;

@Component
public class SubscriptionMapper {
    private final ModelMapper modelMapper;

    public SubscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public SubscriptionDTO toDto(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());
        dto.setOfertante(subscription.getOfertante().getName() + " " + subscription.getOfertante().getLastName());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setCreateDate(subscription.getCreateDate());
        dto.setUpdateDate(subscription.getUpdateDate());
        dto.setStatus(subscription.getStatus());
        dto.setPaymentFrequency(subscription.getPaymentFrequency());
        dto.setMonthlyRate(subscription.getMonthlyRate());
        dto.setTotalAmount(subscription.getTotalAmount());
        return dto;
    }

    public Subscription toEntity(SubscriptionCreateDTO subscriptionDTO) {
        return this.modelMapper.map(subscriptionDTO, Subscription.class);
    }

    public Subscription toEntity(SubscriptionUpdateDTO subscriptionUpdateDTO) {
        return this.modelMapper.map(subscriptionUpdateDTO, Subscription.class);
    }
}
