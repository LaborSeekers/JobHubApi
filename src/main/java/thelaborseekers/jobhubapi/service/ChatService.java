package thelaborseekers.jobhubapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thelaborseekers.jobhubapi.dto.ChatConversationDTO;
import thelaborseekers.jobhubapi.dto.ChatMessageDTO;

import java.util.List;

public interface ChatService {
    List<ChatConversationDTO> getConversationsFromUserId(Integer userId);
    Page<ChatMessageDTO> getOlderMessagesFromConversationId(Integer conversationId, Integer lastMessageId, Integer userId, Pageable pageable);
    ChatConversationDTO createNewConversation(Integer user1Id, Integer user2Id);
    void notifyNewConversation(Integer recipientUserId, ChatConversationDTO conversationId);
    void sendMessage(Integer userId, Integer conversationId, Integer user1, Integer user2,  String content);
}
