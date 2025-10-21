package com.example.event.ticket.booking.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings", uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "user_id"}))
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


    @Column(name = "created_at")
    private
    LocalDateTime createdAt;

    public Booking(Long eventId, AppUser user) {
        this.eventId = eventId;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

}
