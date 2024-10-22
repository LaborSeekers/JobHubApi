package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> findAllPayments();
    List<PaymentDTO> findAllPaymentsFromOfertante(Integer ofertante);
    Page<PaymentDTO> paginatePaymentsFromOfertante(Integer ofertante, Pageable pageable);
}
