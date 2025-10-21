package com.example.event.ticket.booking.system.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventStatusResponse {
    private Long eventId;

    private String eventName;

    private int availableTickets;

    private int waitingListCount;
}
