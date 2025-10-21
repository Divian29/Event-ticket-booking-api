package com.example.event.ticket.booking.system.service;

import com.example.event.ticket.booking.system.entity.AppUser;
import com.example.event.ticket.booking.system.entity.Booking;
import com.example.event.ticket.booking.system.entity.Event;
import com.example.event.ticket.booking.system.entity.WaitingListEntry;
import com.example.event.ticket.booking.system.payload.*;
import com.example.event.ticket.booking.system.repository.BookingRepository;
import com.example.event.ticket.booking.system.repository.EventRepository;
import com.example.event.ticket.booking.system.repository.UserRepository;
import com.example.event.ticket.booking.system.repository.WaitingListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j

public class EventService {
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final WaitingListRepository waitingListRepository;
    private final UserRepository userRepository;
    private final ReentrantLock lock = new ReentrantLock(true); // Fair lock for concurrency


    @Transactional
    public InitializeEventResponse initializeEvent(InitializeEventRequest request) {
        log.info("Initializing event: {}", request.getEventName());
        Event event = new Event();
        event.setName(request.getEventName());
        event.setTotalTickets(request.getTotalTickets());
        event.setAvailableTickets(request.getTotalTickets());
        Event savedEvent = eventRepository.save(event);
        log.info("Event initialized with ID: {}", savedEvent.getId());


        return InitializeEventResponse.builder()
                .eventId(savedEvent.getId())
                .eventName(savedEvent.getName())
                .totalTickets(savedEvent.getTotalTickets())
                .availableTickets(savedEvent.getAvailableTickets())
                .message("Event initialized successfully")
                .build();
    }


    @Transactional
    public BookTicketResponse bookTicket(BookTicketRequest request) {
        lock.lock();
        try {
            Event event = eventRepository.findByIdForUpdate(request.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            if (bookingRepository.findByEventIdAndUserId(request.getEventId(), request.getUserId()).isPresent()) {
                return new BookTicketResponse(request.getEventId(), request.getUserId(), "FAILED", "User already booked this event.");
            }

            if (event.getAvailableTickets() > 0) {
                event.decrementAvailableTickets(1);
                eventRepository.save(event);

                AppUser user = userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                bookingRepository.save(new Booking(event.getId(), user));
                return new BookTicketResponse(request.getEventId(), request.getUserId(), "SUCCESS", "Ticket booked successfully!");
            } else {
                if (!waitingListRepository.existsByEventIdAndUserId(event.getId(), request.getUserId())) {
                    waitingListRepository.save(new WaitingListEntry(event.getId(), request.getUserId()));
                }
                return new BookTicketResponse(request.getEventId(), request.getUserId(), "WAITLISTED", "Event sold out. Added to waiting list.");
            }
        } finally {
            lock.unlock();
        }
    }


    @Transactional
    public CancelBookingResponse cancelBooking(CancelBookingRequest request) {
        lock.lock();
        try {
            Event event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            bookingRepository.findByEventIdAndUserId(event.getId(), request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            bookingRepository.deleteByEventIdAndUserId(event.getId(), request.getUserId());

            waitingListRepository.findFirstByEventIdOrderByIdAsc(event.getId())
                    .ifPresentOrElse(waitingUser -> {
                        AppUser nextUser = userRepository.findById(Long.valueOf(waitingUser.getUserId()))
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        bookingRepository.save(new Booking(event.getId(), nextUser));
                        waitingListRepository.delete(waitingUser);
                    }, () -> {
                        event.setAvailableTickets(event.getAvailableTickets() + 1);
                        eventRepository.save(event);
                    });

            return new CancelBookingResponse(request.getEventId(), request.getUserId(), "Booking cancelled successfully.");
        } finally {
            lock.unlock();
        }
    }

    public EventStatusResponse getEventStatus(Long eventId) {
        // Fetch the event by its ID from the repository
        // Throws RuntimeException if the event does not exist
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Count the number of users in the waiting list for this event
        // Streams through all waiting list entries and filters by eventId
        int waitingCount = (int) waitingListRepository.findAll()
                .stream().filter(w -> w.getEventId().equals(eventId)).count();

        // Create and return a response object (DTO) containing:
        // - Event ID
        // - Event name
        // - Number of available tickets
        // - Number of users on the waiting list
        return new EventStatusResponse(
                event.getId(),
                event.getName(),
                event.getAvailableTickets(),
                waitingCount
        );
    }

}
