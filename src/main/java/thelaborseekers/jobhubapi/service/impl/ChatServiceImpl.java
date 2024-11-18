package thelaborseekers.jobhubapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import thelaborseekers.jobhubapi.dto.ChatConversationDTO;
import thelaborseekers.jobhubapi.dto.ChatMessageDTO;
import thelaborseekers.jobhubapi.mapper.ChatMapper;
import thelaborseekers.jobhubapi.model.entity.Conversation;
import thelaborseekers.jobhubapi.model.entity.Message;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.repository.ConversationRepository;
import thelaborseekers.jobhubapi.repository.MessageRepository;
import thelaborseekers.jobhubapi.repository.UserRepository;
import thelaborseekers.jobhubapi.service.ChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMapper chatMapper;

    public List<ChatConversationDTO> getConversationsFromUserId(Integer userId) {
        List<Conversation> list = conversationRepository.findByUserId(userId);
        return list.stream()
                .map(chatMapper::toChatConversationDTO)
                .toList();
    }

    @Override
    public Page<ChatMessageDTO> getOlderMessagesFromConversationId(Integer conversationId, Integer lastMessageId, Integer userId, Pageable pageable) {
        Page<Message> list = messageRepository.findByConversationIdAndIdLessThanOrderByCreatedAtDesc(
                conversationId, lastMessageId, pageable);

        Message lastMessage = list.getContent().isEmpty() ? null : list.getContent().getFirst();

        if (lastMessage != null && !lastMessage.isRead()) {
            if(!lastMessage.getSender().getId().equals(userId)) {
                lastMessage.setRead(true);
                messageRepository.save(lastMessage);
            }
        }

        return list.map(chatMapper::toChatMessageDTO);
    }


    public ChatConversationDTO createNewConversation(Integer user1Id, Integer user2Id) {
        Conversation existingConversation = conversationRepository
                .findByUsers(user1Id, user2Id);

        if (existingConversation != null) {
            return chatMapper.toChatConversationDTO(existingConversation);
        }

        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("User not found"));

        Conversation newConversation = new Conversation();
        newConversation.setUser_1(user1);
        newConversation.setUser_2(user2);
        newConversation = conversationRepository.save(newConversation);
        return chatMapper.toChatConversationDTO(newConversation);
    }

    public void notifyNewConversation(Integer recipientUserId, ChatConversationDTO conversationId) {
        messagingTemplate.convertAndSendToUser(
                recipientUserId.toString(), "/queue/newConversation", conversationId);
    }

    public void sendMessage(Integer userId, Integer conversationId, Integer user1, Integer user2, String content) {
        if (!user1.equals(userId) && !user2.equals(userId)) {
            throw new RuntimeException("User is not part of this conversation");
        }

        if (!userId.equals(user1)) {
            messagingTemplate.convertAndSend("/topic/conversation/" + conversationId + "/user/" + user1, content);
        }
        else if (!userId.equals(user2)) {
            messagingTemplate.convertAndSend("/topic/conversation/" + conversationId + "/user/" + user2, content);
        }
        else{
            messagingTemplate.convertAndSend("/topic/conversation/" + conversationId + "/user/" + user2, content);
        }

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(content);

        conversation.setLastMessage(message);

        messageRepository.save(message);
        conversationRepository.save(conversation);
    }
}
