package com.example.event.ticket.booking.system.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BookTicketRequest {
    @NotNull(message = "Event ID cannot be blank")
    private Long eventId;

    @NotNull(message = "User ID cannot be blank")
    private Long userId;
}
