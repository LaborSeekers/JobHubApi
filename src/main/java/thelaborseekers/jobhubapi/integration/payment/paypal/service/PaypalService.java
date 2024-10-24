package thelaborseekers.jobhubapi.integration.payment.paypal.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import thelaborseekers.jobhubapi.exception.ResourceNotFoundException;
import thelaborseekers.jobhubapi.integration.payment.paypal.dto.*;
import thelaborseekers.jobhubapi.model.entity.Subscription;
import thelaborseekers.jobhubapi.repository.SubscriptionRepository;

import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PaypalService {
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.api-base}")
    private String apiBase;

    private final SubscriptionRepository subscriptionRepository;

    private RestClient  paypalClient;

    @PostConstruct
    public void init(){
        paypalClient = RestClient
                .builder()
                .baseUrl(apiBase)
                .build();
    }

    public String getAccessToken(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        return Objects.requireNonNull(
                paypalClient.post()
                        .uri("/v1/oauth2/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(HttpHeaders.AUTHORIZATION, "Basic "+ Base64.getEncoder()
                                .encodeToString((clientId + ":" + clientSecret).getBytes()))
                        .body(body)
                        .retrieve()
                        .toEntity(TokenResponse.class).getBody())
                .getAccessToken();
    }

    public OrderResponse createOrder(Integer subscriptionID, String returnURL, String cancelURL){
        Subscription subscription = subscriptionRepository.findById(subscriptionID).orElseThrow(ResourceNotFoundException::new);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIntent("CAPTURE");

        Amount amount = new Amount();
        amount.setCurrencyCode("USD");
        amount.setValue(subscription.getTotalAmount().toString());

        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setReferenceId(subscription.getId().toString());
        purchaseUnit.setAmount(amount);

        orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));

        ApplicationContext applicationContext = ApplicationContext.builder()
                .brandName("JobHub")
                .shippingPreference("NO_SHIPPING")
                .returnURL(returnURL)
                .cancelURL(cancelURL)
                .build();

        orderRequest.setApplicationContext(applicationContext);

        return paypalClient.post()
                .uri("/v2/checkout/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .body(orderRequest)
                .retrieve()
                .toEntity(OrderResponse.class)
                .getBody();
    }

    public OrderCaptureResponse captureOrder(String orderId){
        return paypalClient.post()
                .uri("v2/checkout/orders/{order_id}/capture", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .retrieve()
                .toEntity(OrderCaptureResponse.class)
                .getBody();
    }
}
