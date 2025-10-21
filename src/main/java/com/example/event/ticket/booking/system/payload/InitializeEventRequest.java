package com.example.event.ticket.booking.system.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class InitializeEventRequest {
    @NotBlank(message = "Event name cannot be blank")
    private String eventName;

    @Min(value = 1, message = "Number of tickets must be at least 1")
    private int totalTickets;
}
