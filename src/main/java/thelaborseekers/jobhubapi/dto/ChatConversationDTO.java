package thelaborseekers.jobhubapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatConversationDTO {
    private Integer id;

    private Integer user_1;
    private String user_1_name;
    private Integer user_2;
    private String user_2_name;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private ChatMessageDTO lastMessage;
}
