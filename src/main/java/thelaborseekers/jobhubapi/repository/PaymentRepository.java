package thelaborseekers.jobhubapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByOfertanteId(Integer ofertanteId);
    Page<Payment> findByOfertanteId(Integer ofertanteId, Pageable pageable);
}
