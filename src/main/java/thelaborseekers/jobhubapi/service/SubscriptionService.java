package thelaborseekers.jobhubapi.service;

import jakarta.mail.MessagingException;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionCreateDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionDTO;
import thelaborseekers.jobhubapi.dto.SubscriptionUpdateDTO;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionCreateDTO subscription);
    SubscriptionDTO updateSubscription(Integer id, SubscriptionUpdateDTO subscription);
    void cancelSubscription(Integer subscriptionId);
    void deleteSubscription(Integer subscriptionId);
    void paymentDecline(Integer subscriptionId);
    SubscriptionDTO findById(Integer subscriptionId);
    SubscriptionDTO findByOfertanteId(Integer ofertanteId);
    PaymentDTO confirmPayment(Integer subscriptionId);
    void checkSubscriptions() throws MessagingException;
}
