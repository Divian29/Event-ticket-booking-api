package com.example.event.ticket.booking.system;

import com.example.event.ticket.booking.system.entity.Event;
import com.example.event.ticket.booking.system.payload.BookTicketRequest;
import com.example.event.ticket.booking.system.payload.InitializeEventRequest;
import com.example.event.ticket.booking.system.payload.InitializeEventResponse;
import com.example.event.ticket.booking.system.repository.BookingRepository;
import com.example.event.ticket.booking.system.repository.EventRepository;
import com.example.event.ticket.booking.system.repository.UserRepository;
import com.example.event.ticket.booking.system.repository.WaitingListRepository;
import com.example.event.ticket.booking.system.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
class EventTicketBookingSystemApplicationTests {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private WaitingListRepository waitingListRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void initializeEvent_shouldCreateNewEvent() {
        // Arrange
        InitializeEventRequest request = new InitializeEventRequest();
        request.setEventName("Concert");
        request.setTotalTickets(100);

        Event savedEvent = new Event();
        savedEvent.setId(1L); // must set ID
        savedEvent.setName("Concert");
        savedEvent.setTotalTickets(100);
        savedEvent.setAvailableTickets(100);

        // Mock save() to return our savedEvent
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // Act
        InitializeEventResponse response = eventService.initializeEvent(request);

        // Assert
        assertNotNull(response);
        assertEquals("Concert", response.getEventName());
        assertEquals(100, response.getAvailableTickets());
        assertEquals(1L, response.getEventId()); // check ID
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void bookTicket_shouldThrowErrorWhenEventNotFound() {
        BookTicketRequest request = new BookTicketRequest();
        request.setEventId(1L);
        request.setUserId(2L);

        when(eventRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> eventService.bookTicket(request));

        assertEquals("Event not found", exception.getMessage());
    }



}
