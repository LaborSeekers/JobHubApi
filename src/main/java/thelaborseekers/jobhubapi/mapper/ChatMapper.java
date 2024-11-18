package thelaborseekers.jobhubapi.mapper;

import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.ChatConversationDTO;
import thelaborseekers.jobhubapi.dto.ChatMessageDTO;
import thelaborseekers.jobhubapi.model.entity.*;

@Component
public class ChatMapper {

    public ChatMessageDTO toChatMessageDTO(Message message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setConversation(message.getConversation().getId());
        dto.setSender(message.getSender().getId());
        dto.setContent(message.getContent());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setRead(message.isRead());
        return dto;
    }

    private String getUserFullName(User user) {
        if (user.getOfertante() != null) {
            Ofertante ofertante = user.getOfertante();
            return ofertante.getFirstName() + " " + ofertante.getLastName();
        } else if (user.getPostulante() != null) {
            Postulante postulante = user.getPostulante();
            return postulante.getFirstName() + " " + postulante.getLastName();
        }
        return "";
    }

    public ChatConversationDTO toChatConversationDTO(Conversation conversation) {
        ChatConversationDTO dto = new ChatConversationDTO();
        dto.setId(conversation.getId());

        dto.setUser_1(conversation.getUser_1().getId());
        dto.setUser_2(conversation.getUser_2().getId());

        dto.setUser_1_name(getUserFullName(conversation.getUser_1()));
        dto.setUser_2_name(getUserFullName(conversation.getUser_2()));

        dto.setCreated_at(conversation.getCreated_at());
        dto.setUpdated_at(conversation.getUpdated_at());

        if (conversation.getLastMessage() != null) {
            dto.setLastMessage(toChatMessageDTO(conversation.getLastMessage()));
        }

        return dto;
    }
}
