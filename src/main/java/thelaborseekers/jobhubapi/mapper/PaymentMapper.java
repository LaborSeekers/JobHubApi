package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.model.entity.Payment;

@Component
public class PaymentMapper {
    private final ModelMapper modelMapper;

    public PaymentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public PaymentDTO toDto(Payment payment) {

        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setOfertante(payment.getOfertante().getFirstName() + " " + payment.getOfertante().getLastName());
        dto.setType(payment.getType());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setQuantity(payment.getQuantity());
        dto.setTotal(payment.getTotal());
        dto.setMethod(payment.getMethod());
        dto.setDescription(payment.getDescription());
        return dto;
    }

}
