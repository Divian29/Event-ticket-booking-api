package com.example.event.ticket.booking.system.repository;

import com.example.event.ticket.booking.system.entity.WaitingListEntry;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitingListRepository extends JpaRepository<WaitingListEntry, Long> {
    List<WaitingListEntry> findByEventIdOrderByJoinedAtAsc(Long eventId);
    long countByEventId(Long eventId);

    Optional<WaitingListEntry> findFirstByEventIdOrderByIdAsc(Long eventId);

    boolean existsByEventIdAndUserId(Long id, @NotBlank(message = "User ID cannot be blank") Long userId);
}
