package thelaborseekers.jobhubapi.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.PaymentCaptureResponse;
import thelaborseekers.jobhubapi.dto.PaymentDTO;
import thelaborseekers.jobhubapi.dto.PaymentOrderResponse;
import thelaborseekers.jobhubapi.integration.notification.email.dto.Mail;
import thelaborseekers.jobhubapi.integration.notification.email.service.EmailService;
import thelaborseekers.jobhubapi.integration.payment.paypal.dto.Link;
import thelaborseekers.jobhubapi.integration.payment.paypal.dto.OrderCaptureResponse;
import thelaborseekers.jobhubapi.integration.payment.paypal.dto.OrderResponse;
import thelaborseekers.jobhubapi.integration.payment.paypal.service.PaypalService;
import thelaborseekers.jobhubapi.service.CheckoutService;
import thelaborseekers.jobhubapi.service.SubscriptionService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final PaypalService paypalService;
    private final SubscriptionService subscriptionService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public PaymentOrderResponse createPayment(Integer subscriptionId, String returnUrl, String cancelUrl) {
        OrderResponse orderResponse = paypalService.createOrder(subscriptionId, returnUrl, cancelUrl);

        for(Link a : orderResponse.getLinks()){
            System.out.println(a);
        }
        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);
    }

    @Override
    public PaymentCaptureResponse capturePayment(String orderId) throws MessagingException {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if(completed) {
            String subscriptionIdStr = orderCaptureResponse.getPurchaseUnits().getFirst().getReferenceId();
            Integer subscriptionId = Integer.parseInt(subscriptionIdStr);
            PaymentDTO paymentDTO = subscriptionService.confirmPayment(subscriptionId);
            paypalCaptureResponse.setSubscriptionId(subscriptionId);

            sendPaymentConfirmationEmail(paymentDTO);
        }
        return paypalCaptureResponse;
    }

    private void sendPaymentConfirmationEmail(PaymentDTO paymentDTO) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Map<String, Object> model = new HashMap<>();
        model.put("id", paymentDTO.getId());
        model.put("paymentDate", paymentDTO.getPaymentDate());
        model.put("user", paymentDTO.getOfertante());
        model.put("description", paymentDTO.getDescription());
        model.put("type", paymentDTO.getType());
        model.put("quantity", paymentDTO.getQuantity());
        model.put("amount", paymentDTO.getAmount());
        model.put("total", paymentDTO.getTotal());

        Mail mail = emailService.createMail(
                userEmail,
                "JobHub | Confirmacion de compra",
                model,
                mailFrom
        );
        emailService.sendMail(mail, "email/billing-template");
    }
}
