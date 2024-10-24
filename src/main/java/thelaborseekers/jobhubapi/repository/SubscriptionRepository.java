package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Subscription;
import thelaborseekers.jobhubapi.model.enums.SubscriptionStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findByOfertanteId(Integer userId);
    List<Subscription> findByEndDateBeforeAndStatus(LocalDate dateTime, SubscriptionStatus status);
    List<Subscription> findByEndDateBetweenAndStatus(
            LocalDate startDateTime,
            LocalDate endDateTime,
            SubscriptionStatus status
    );
}
