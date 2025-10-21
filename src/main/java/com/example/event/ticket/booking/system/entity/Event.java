package com.example.event.ticket.booking.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")


public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int totalTickets;

    private int availableTickets;

    private int ticketsSold;

    @Version
    private Long version;

    public Long getVersion() { return version; }

    public void setVersion(Long version) { this.version = version; }


    public void decrementAvailableTickets(int n) {
        this.availableTickets -= n;
    }

    public void incrementAvailableTickets(int n) {
        this.availableTickets += n;
    }
}
