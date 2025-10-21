package com.example.event.ticket.booking.system.payload;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;

    private String email;

    private String password;
}
