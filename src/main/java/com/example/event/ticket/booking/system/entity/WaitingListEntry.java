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
@Table(name = "waiting_list")
public class WaitingListEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "event_id")
    private Long eventId;


    @Column(name = "user_id")
    private Long userId;


    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    public WaitingListEntry(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
        this.joinedAt = LocalDateTime.now();
    }

}
