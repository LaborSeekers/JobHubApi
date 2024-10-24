package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.mapper.PaymentMapper;
import thelaborseekers.jobhubapi.model.entity.Payment;
import thelaborseekers.jobhubapi.repository.PaymentRepository;
import thelaborseekers.jobhubapi.service.PaymentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    @Override
    public List<PaymentDTO> findAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentDTO> findAllPaymentsFromOfertante(Integer ofertante) {
        List<Payment> payments = paymentRepository.findByOfertanteId(ofertante);

        return payments.stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public Page<PaymentDTO> paginatePaymentsFromOfertante(Integer ofertante, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByOfertanteId(ofertante, pageable);
        return payments.map(paymentMapper::toDto);
    }
}
