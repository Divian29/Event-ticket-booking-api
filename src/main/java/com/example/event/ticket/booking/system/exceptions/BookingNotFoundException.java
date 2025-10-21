package com.example.event.ticket.booking.system.exceptions;

public class BookingNotFoundException extends RuntimeException {
        public BookingNotFoundException(String message) {
            super(message);
        }
}
