package thelaborseekers.jobhubapi.mapper;

import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.model.entity.Payment;

@Component
public class PaymentMapper {

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
