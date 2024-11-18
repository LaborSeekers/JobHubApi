package thelaborseekers.jobhubapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import thelaborseekers.jobhubapi.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Page<Message> findByConversationIdAndIdLessThanOrderByCreatedAtDesc(
            Integer conversationId,
            Integer lastMessageId,
            Pageable pageable
    );
}
