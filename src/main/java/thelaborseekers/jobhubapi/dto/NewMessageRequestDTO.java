package thelaborseekers.jobhubapi.dto;

import lombok.Data;

@Data
public class NewMessageRequestDTO {
    private Integer userId;
    private Integer conversationId;
    private Integer user1;
    private Integer user2;
    private String content;
}
