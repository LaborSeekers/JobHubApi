package thelaborseekers.jobhubapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private Integer id;
    private Integer conversation;
    private Integer sender;
    private String content;
    private LocalDateTime createdAt;
    private boolean read;
}
