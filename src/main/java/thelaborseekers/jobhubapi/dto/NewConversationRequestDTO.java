package thelaborseekers.jobhubapi.dto;

import lombok.Data;

@Data
public class NewConversationRequestDTO {
    private Integer senderId;
    private Integer recipientId;
}
