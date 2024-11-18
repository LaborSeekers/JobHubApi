package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import thelaborseekers.jobhubapi.dto.ChatConversationDTO;
import thelaborseekers.jobhubapi.dto.NewConversationRequestDTO;
import thelaborseekers.jobhubapi.dto.NewMessageRequestDTO;
import thelaborseekers.jobhubapi.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChatWSController {

    private final ChatService chatService;

    @MessageMapping("/chat/newConversation")
    public void handleNewConversationRequest (@Payload NewConversationRequestDTO conversationRequestDTO) {
        Integer senderUserId = conversationRequestDTO.getSenderId();
        Integer recipientUserIdParsed = conversationRequestDTO.getRecipientId();

        ChatConversationDTO newConversation = chatService.createNewConversation(senderUserId, recipientUserIdParsed);

        chatService.notifyNewConversation(senderUserId, newConversation);
        chatService.notifyNewConversation(recipientUserIdParsed, newConversation);
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload NewMessageRequestDTO messageRequestDTO) {
        Integer userId = messageRequestDTO.getUserId();
        Integer conversationId = messageRequestDTO.getConversationId();
        Integer user1 = messageRequestDTO.getUser1();
        Integer user2 = messageRequestDTO.getUser2();
        String content = messageRequestDTO.getContent();

        chatService.sendMessage(userId, conversationId, user1, user2,  content);
    }

}
