package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thelaborseekers.jobhubapi.dto.ChatConversationDTO;
import thelaborseekers.jobhubapi.dto.ChatMessageDTO;
import thelaborseekers.jobhubapi.service.ChatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/conversations")
    public ResponseEntity<List<ChatConversationDTO>> getConversationsFromUser(@RequestParam("userId") Integer userId) {
        List<ChatConversationDTO> list = chatService.getConversationsFromUserId(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/older")
    public ResponseEntity<List<ChatMessageDTO>> getOlderMessages(
            @RequestParam Integer conversationId,
            @RequestParam Integer lastMessageId,
            @RequestParam Integer userId,
            Pageable pageable
    ) {
        Page<ChatMessageDTO> messages = chatService.getOlderMessagesFromConversationId(conversationId, lastMessageId, userId ,pageable);
        List<ChatMessageDTO> messageDTOs = messages.getContent();
        return ResponseEntity.ok(messageDTOs);
    }
}
