package thelaborseekers.jobhubapi.service;

import jakarta.mail.MessagingException;
import thelaborseekers.jobhubapi.dto.PaymentCaptureResponse;
import thelaborseekers.jobhubapi.dto.PaymentOrderResponse;

public interface CheckoutService {

    PaymentOrderResponse createPayment(Integer subscriptionId, String returnUrl, String cancelUrl);

    PaymentCaptureResponse capturePayment(String orderId) throws MessagingException;
}
