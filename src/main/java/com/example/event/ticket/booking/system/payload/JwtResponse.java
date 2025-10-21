package com.example.event.ticket.booking.system.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class JwtResponse {
    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
