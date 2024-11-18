package thelaborseekers.jobhubapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelaborseekers.jobhubapi.model.entity.Conversation;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    @Query("SELECT c FROM Conversation c WHERE (c.user_1.id = :user1Id AND c.user_2.id = :user2Id) OR (c.user_1.id = :user2Id AND c.user_2.id = :user1Id)")
    Conversation findByUsers(@Param("user1Id") Integer user1Id, @Param("user2Id") Integer user2Id);

    @Query("SELECT c FROM Conversation c WHERE (c.user_2.id = :userId OR c.user_1.id = :userId) ORDER BY c.updated_at")
    List<Conversation> findByUserId(@Param("userId") Integer user1Id);
}
