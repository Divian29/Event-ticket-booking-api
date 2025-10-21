package com.example.event.ticket.booking.system.controller;

import com.example.event.ticket.booking.system.entity.Event;
import com.example.event.ticket.booking.system.payload.*;
import com.example.event.ticket.booking.system.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/initialize")
    public ResponseEntity<InitializeEventResponse> initializeEvent(@RequestBody @Valid InitializeEventRequest request) {
        return ResponseEntity.ok(eventService.initializeEvent(request));
    }

    @PostMapping("/book")
    public ResponseEntity<BookTicketResponse> bookTicket(@RequestBody @Valid BookTicketRequest request) {
        return ResponseEntity.ok(eventService.bookTicket(request));
    }

    @PostMapping("/cancel")
    public ResponseEntity<CancelBookingResponse> cancelBooking(@RequestBody @Valid CancelBookingRequest request) {
        return ResponseEntity.ok(eventService.cancelBooking(request));
    }

    @GetMapping("/{eventId}/status")
    public ResponseEntity<EventStatusResponse> getEventStatus(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventStatus(eventId));
    }

}
