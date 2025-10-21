package com.example.event.ticket.booking.system.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTicketResponse {
    private Long eventId;

    private Long userId;

    private String status;   // e.g. "SUCCESS", "WAITING_LIST", "FAILED"

    private String message;  // e.g. "Ticket booked successfully!" or "Added to waiting list."
}
