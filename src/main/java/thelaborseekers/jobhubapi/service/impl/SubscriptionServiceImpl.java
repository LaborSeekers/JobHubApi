package thelaborseekers.jobhubapi.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionCreateDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionUpdateDTO;
import thelaborseekers.jobhubapi.exception.BadRequestException;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.integration.notification.email.dto.Mail;
import thelaborseekers.jobhubapi.integration.notification.email.service.EmailService;
import thelaborseekers.jobhubapi.mapper.PaymentMapper;
import thelaborseekers.jobhubapi.mapper.SubscriptionMapper;
import thelaborseekers.jobhubapi.model.entity.Ofertante;
import thelaborseekers.jobhubapi.model.entity.Payment;
import thelaborseekers.jobhubapi.model.entity.Subscription;
import thelaborseekers.jobhubapi.model.enums.PaymentType;
import thelaborseekers.jobhubapi.model.enums.SubscriptionStatus;
import thelaborseekers.jobhubapi.repository.OfertanteRepository;
import thelaborseekers.jobhubapi.repository.PaymentRepository;
import thelaborseekers.jobhubapi.repository.SubscriptionRepository;
import thelaborseekers.jobhubapi.service.SubscriptionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OfertanteRepository ofertanteRepository;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${JobHub.domain}")
    private String domain;

    @Override
    public SubscriptionDTO createSubscription(SubscriptionCreateDTO subscription) {
        Ofertante ofertante = ofertanteRepository.findById(subscription.getOfertante())
                .orElseThrow(()-> new ResourceNotFoundException("Ofertante not found with id: " + subscription.getOfertante()));

        subscriptionRepository.findByOfertanteId(ofertante.getId())
                .ifPresent(subs -> {
            throw new BadRequestException("Ya existe una subscripcion para ese usuario");
        });

        Subscription subscriptionEntity = new Subscription();
        subscriptionEntity.setOfertante(ofertante);
        subscriptionEntity.setCreateDate(LocalDate.now());
        subscriptionEntity.setStatus(SubscriptionStatus.INACTIVE);
        subscriptionEntity.setPaymentFrequency(subscription.getPaymentFrequency());
        subscriptionEntity.setMonthlyRate(10.f);

        switch (subscription.getPaymentFrequency()){
            case MONTHLY -> subscriptionEntity.setTotalAmount(subscriptionEntity.getMonthlyRate());

            case YEARLY -> subscriptionEntity.setTotalAmount(subscriptionEntity.getMonthlyRate() * 12 * 0.8f);
        }

        subscriptionEntity = subscriptionRepository.save(subscriptionEntity);
        return subscriptionMapper.toDto(subscriptionEntity);
    }

    @Override
    public SubscriptionDTO updateSubscription(Integer id, SubscriptionUpdateDTO subscription) {
        Subscription subscriptionDB = subscriptionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Subscription not found with id: " + id));

        subscriptionDB.setPaymentFrequency(subscription.getPaymentFrequency());
        subscriptionDB.setUpdateDate(LocalDate.now());

        switch (subscription.getPaymentFrequency()){
            case MONTHLY -> subscriptionDB.setTotalAmount(subscriptionDB.getMonthlyRate());
            case YEARLY -> subscriptionDB.setTotalAmount(subscriptionDB.getMonthlyRate() * 12 * 0.8f);
        }

        subscriptionDB = subscriptionRepository.save(subscriptionDB);
        return subscriptionMapper.toDto(subscriptionDB);
    }

    @Override
    public void cancelSubscription(Integer subscriptionId) {
        Subscription subscriptionDB = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        subscriptionDB.setStatus(SubscriptionStatus.CANCELLED);

        subscriptionRepository.save(subscriptionDB);
    }

    @Override
    public void deleteSubscription(Integer subscriptionId) {
        Subscription subscriptionDB = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));
        subscriptionRepository.delete(subscriptionDB);
    }

    @Override
    public void paymentDecline(Integer subscriptionId) {
        Subscription subscriptionDB = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));
        if (subscriptionDB.getStatus() == SubscriptionStatus.INACTIVE)
            subscriptionRepository.delete(subscriptionDB);
    }

    @Override
    public SubscriptionDTO findById(Integer subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).
                orElseThrow(()-> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));
        return subscriptionMapper.toDto(subscription);
    }

    @Override
    public SubscriptionDTO findByOfertanteId(Integer ofertanteId) {
        Subscription subscription = subscriptionRepository.findByOfertanteId(ofertanteId).
                orElseThrow(()-> new ResourceNotFoundException("Subscription not found with id: " + ofertanteId));
        return subscriptionMapper.toDto(subscription);
    }

    @Override
    public PaymentDTO confirmPayment(Integer subscriptionId) {
        Subscription subscriptionDB = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(()-> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        Payment payment = new Payment();
        payment.setOfertante(subscriptionDB.getOfertante());
        payment.setType(PaymentType.SUBSCRIPTION);
        payment.setMethod("paypal");
        payment.setQuantity(1);
        payment.setPaymentDate(LocalDateTime.now());

        switch (subscriptionDB.getPaymentFrequency()){
            case MONTHLY -> {
                payment.setAmount(subscriptionDB.getTotalAmount());
                payment.setDescription("Pago mensual de subscripcion");
            }
            case YEARLY -> {
                payment.setAmount(subscriptionDB.getTotalAmount());
                payment.setDescription("Pago anual de subscripcion");
            }
        }

        payment.setTotal(payment.getAmount() * payment.getQuantity());
        payment = paymentRepository.save(payment);


        LocalDate now = LocalDate.now();
        if(subscriptionDB.getStatus() == SubscriptionStatus.CANCELLED){
            if(subscriptionDB.getEndDate().isAfter(now)){
                subscriptionDB.setStatus(SubscriptionStatus.ACTIVE);
            }
        }

        if (subscriptionDB.getStatus() == SubscriptionStatus.ACTIVE || subscriptionDB.getStatus() == SubscriptionStatus.EXPIRE_SOON) {
            switch (subscriptionDB.getPaymentFrequency()) {
                case MONTHLY -> subscriptionDB.setEndDate(subscriptionDB.getEndDate().plusMonths(1));
                case YEARLY -> subscriptionDB.setEndDate(subscriptionDB.getEndDate().plusYears(1));
            }
        } else {
            subscriptionDB.setStartDate(now);
            switch (subscriptionDB.getPaymentFrequency()) {
                case MONTHLY -> subscriptionDB.setEndDate(now.plusMonths(1));
                case YEARLY -> subscriptionDB.setEndDate(now.plusYears(1));
            }
        }
        subscriptionDB.setStatus(SubscriptionStatus.ACTIVE);

        subscriptionRepository.save(subscriptionDB);
        return paymentMapper.toDto(payment);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void checkSubscriptions() throws MessagingException {
        System.out.println("Act");
        LocalDate now = LocalDate.now();
        LocalDate tenDaysFromNow = now.plusDays(10);

        List<Subscription> expiringSoon = subscriptionRepository.findByEndDateBetweenAndStatus(
                now, tenDaysFromNow, SubscriptionStatus.ACTIVE
        );
        for (Subscription subscription : expiringSoon) {
            subscription.setStatus(SubscriptionStatus.EXPIRE_SOON);
            subscriptionRepository.save(subscription);
            sendRemainderEmail(subscription);
        }

        List<Subscription> expiredSubscriptions = subscriptionRepository.findByEndDateBeforeAndStatus(now, SubscriptionStatus.EXPIRE_SOON);
        subscriptionRepository.deleteAll(expiredSubscriptions);
    }

    private void sendRemainderEmail(Subscription subscriptionDTO) throws MessagingException {
        String userEmail = subscriptionDTO.getOfertante().getUser().getEmail();

        Map<String, Object> model = new HashMap<>();
        model.put("user", subscriptionDTO.getOfertante());
        model.put("expirationDate", subscriptionDTO.getEndDate());
        model.put("paymentFrequency", subscriptionDTO.getPaymentFrequency());
        model.put("actualPrice", subscriptionDTO.getTotalAmount());
        model.put("renewalUrl", domain + "/Ofertantes/subscription");

        Mail mail = emailService.createMail(
                userEmail,
                "JobHub | Recordatorio de subscripcion",
                model,
                mailFrom
        );
        emailService.sendMail(mail, "email/subscription-reminder-template");
    }
}
