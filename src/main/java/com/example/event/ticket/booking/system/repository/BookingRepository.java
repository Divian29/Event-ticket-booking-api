package com.example.event.ticket.booking.system.repository;

import com.example.event.ticket.booking.system.entity.Booking;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
    Optional<Booking> findByEventIdAndUserId(Long eventId, Long userId);
    long countByEventId(Long eventId);

    void deleteByEventIdAndUserId(Long eventId, Long userId);
}
