package thelaborseekers.jobhubapi.dto;

import lombok.Data;

@Data
public class PaymentCaptureResponse {
    private boolean completed;
    private Integer subscriptionId;
}