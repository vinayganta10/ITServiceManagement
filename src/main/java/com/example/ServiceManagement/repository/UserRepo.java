package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.model.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ServiceManagement.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT u
        FROM User u
        WHERE (:cursor IS NULL OR u.id < :cursor)
        ORDER BY u.id DESC
    """)
    List<User> findNextPage(
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}
