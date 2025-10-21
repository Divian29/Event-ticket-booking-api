package com.example.event.ticket.booking.system.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBookingResponse {
    private Long eventId;
    private Long userId;
    private String message; // e.g. "Booking cancelled successfully."
}
