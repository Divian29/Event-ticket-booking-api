package com.example.event.ticket.booking.system.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InitializeEventResponse {
    private Long eventId;
    private String eventName;
    private int totalTickets;
    private int availableTickets;
    private String message; // e.g. "Event initialized successfully."
}
