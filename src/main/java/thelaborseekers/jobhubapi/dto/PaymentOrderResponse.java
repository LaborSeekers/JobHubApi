package thelaborseekers.jobhubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {
    private String orderId;
    private String paypalUrl;
}
