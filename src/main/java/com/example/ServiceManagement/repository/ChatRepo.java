package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepo extends JpaRepository<ChatMessage,Long> {
    @Query("""
        SELECT c
        FROM ChatMessage c
        WHERE c.ticketId = :ticketId
          AND (
                :createdAt IS NULL
                OR c.createdAt > :createdAt
                OR (c.createdAt = :createdAt AND c.id > :id)
              )
        ORDER BY c.createdAt ASC, c.id ASC
    """)
    List<ChatMessage> findNextMessages(
            @Param("ticketId") Long ticketId,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("id") Long id,
            Pageable pageable
    );

}
