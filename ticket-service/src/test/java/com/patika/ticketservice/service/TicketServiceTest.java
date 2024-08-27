package com.patika.ticketservice.service;

import com.patika.ticketservice.client.user.dto.response.UserResponse;
import com.patika.ticketservice.client.user.service.UserClientService;
import com.patika.ticketservice.converter.TicketConverter;
import com.patika.ticketservice.dto.request.TicketRequest;
import com.patika.ticketservice.dto.response.TicketResponse;
import com.patika.ticketservice.model.Booking;
import com.patika.ticketservice.model.Ticket;
import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.producer.KafkaProducer;
import com.patika.ticketservice.repository.BookingRepository;
import com.patika.ticketservice.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private VoyageService voyageService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    private TicketRequest ticketRequest;
    private Ticket ticket;
    private Voyage voyage;

    @BeforeEach
    void setUp() {
        ticketRequest = TicketRequest.builder()
                .citizenshipNumber("1234567890")
                .passengerFirstName("Melih")
                .passengerMiddleName("")
                .passengerLastName("Beşel")
                .gender(com.patika.ticketservice.model.enums.Gender.MALE)
                .build();

        voyage = Voyage.builder()
                .id(1L)
                .originCity("Istanbul")
                .destinationCity("Ankara")
                .voyageDateTime(LocalDateTime.now())
                .price(BigDecimal.valueOf(100.00))
                .travelType(com.patika.ticketservice.model.enums.TravelType.BUS)
                .build();

        ticket = TicketConverter.convertToTicket(ticketRequest);
        ticket.setId(1L);
        ticket.setVoyage(voyage);
        ticket.setPrice(voyage.getPrice());
    }

    @Test
    void createTicket_ShouldCreateTicket_WhenValidRequest() {
        // given
        Mockito.when(voyageService.findById(1L)).thenReturn(Optional.of(voyage));
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // when
        TicketResponse response = ticketService.createTicket(1L, ticketRequest);

        // then
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        assertThat(response.getPassengerFirstName()).isEqualTo("Melih");
        assertThat(response.getVoyageOriginCity()).isEqualTo("Istanbul");
    }

    @Test
    void updateTicket_ShouldUpdateTicket_WhenValidId() {
        // given
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // when
        TicketResponse response = ticketService.updateTicket(1L, ticketRequest);

        // then
        verify(ticketRepository, times(1)).save(ticket);
        assertThat(response.getPassengerFirstName()).isEqualTo("Melih");
    }

    @Test
    void getTicketById_ShouldReturnTicket_WhenValidId() {
        // given
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // when
        TicketResponse response = ticketService.getTicketById(1L);

        // then
        verify(ticketRepository, times(1)).findById(1L);
        assertThat(response.getPassengerFirstName()).isEqualTo("Melih");
    }

    @Test
    void deleteTicketById_ShouldDeleteTicket_WhenValidId() {
        // given
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // when
        TicketResponse response = ticketService.deleteTicketById(1L);

        // then
        verify(ticketRepository, times(1)).deleteById(1L);
        assertThat(response.getPassengerFirstName()).isEqualTo("Melih");
    }

    @Test
    void getAllByEmail_ShouldReturnTickets_WhenUserHasBookings() {
        // given
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(1L);

        Booking booking = new Booking();
        booking.setTicketList(Collections.singletonList(ticket));

        Mockito.when(userClientService.getUserByEmail("mbesel2005@gmail.com")).thenReturn(userResponse);
        Mockito.when(bookingRepository.findByUserId(1L)).thenReturn(Collections.singletonList(booking));

        // when
        List<TicketResponse> responses = ticketService.getAllByEmail("mbesel2005@gmail.com");

        // then
        verify(userClientService, times(1)).getUserByEmail("mbesel2005@gmail.com");
        verify(bookingRepository, times(1)).findByUserId(1L);
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getPassengerFirstName()).isEqualTo("Melih");
    }
}
