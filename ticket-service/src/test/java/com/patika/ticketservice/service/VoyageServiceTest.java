package com.patika.ticketservice.service;

import com.patika.ticketservice.dto.request.BookingRequest;
import com.patika.ticketservice.dto.request.VoyageRequest;
import com.patika.ticketservice.dto.response.VoyageResponse;
import com.patika.ticketservice.model.Ticket;
import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.model.enums.TravelType;
import com.patika.ticketservice.model.enums.VoyageStatus;
import com.patika.ticketservice.producer.KafkaProducer;
import com.patika.ticketservice.repository.VoyageRepository;
import com.patika.ticketservice.util.Constants;
import com.patika.ticketservice.util.DateTimeStringConvertor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoyageServiceTest {

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private VoyageService voyageService;

    private Voyage voyage;
    private VoyageRequest voyageRequest;
    private BookingRequest bookingRequest;
    private final LocalDateTime voyageDateTime = DateTimeStringConvertor.convertStringToLocalDateTime("2024-09-01 10:00");

    @BeforeEach
    void setUp() {
        voyage = new Voyage();
        voyage.setOriginCity("Istanbul");
        voyage.setDestinationCity("Ankara");
        voyage.setVoyageDateTime(voyageDateTime);
        voyage.setTravelType(TravelType.BUS);
        voyage.setPrice(BigDecimal.valueOf(100));
        voyage.setAvailableSeat(Constants.MAX_AVAILABLE_SEATS_FOR_BUS);

        voyageRequest = VoyageRequest.builder()
                .originCity("Istanbul")
                .destinationCity("Ankara")
                .voyageDateTime("2024-09-01 10:00")
                .travelType(TravelType.BUS)
                .price(BigDecimal.valueOf(100))
                .build();

        bookingRequest = BookingRequest.builder()
                .bookingOriginCity("Istanbul")
                .bookingDestinationCity("Ankara")
                .bookingVoyageDateTime("2024-09-01 10:00")
                .bookingTravelType(TravelType.BUS)
                .build();
    }

    @Test
    void createVoyage_ShouldSaveVoyageAndReturnVoyageResponse() {
        when(voyageRepository.save(any(Voyage.class))).thenReturn(voyage);

        VoyageResponse response = voyageService.createVoyage(voyageRequest);

        assertNotNull(response);
        assertEquals(voyageRequest.getOriginCity(), response.getOriginCity());
        assertEquals(voyageRequest.getDestinationCity(), response.getDestinationCity());
        verify(voyageRepository, times(1)).save(any(Voyage.class));
        verify(kafkaProducer).sendLog("VoyageService -->setAvailableSeatForVoyage()--> voyage available seat has been set to initial value.");
        verify(kafkaProducer).sendLog("VoyageService -->createVoyage()--> voyage has been saved to voyageRepository.");
    }

    @Test
    void deactivateVoyage_ShouldDeactivateVoyageAndReturnVoyageResponse() {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));

        VoyageResponse response = voyageService.deactivateVoyage(1L);

        assertNotNull(response);
        assertEquals(VoyageStatus.PASSIVE, voyage.getVoyageStatus());
        verify(voyageRepository, times(1)).save(voyage);
        verify(kafkaProducer, times(1)).sendLog(anyString());
    }

    @Test
    void deleteVoyage_ShouldDeleteVoyageAndReturnVoyageResponse() {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));

        VoyageResponse response = voyageService.deleteVoyage(1L);

        assertNotNull(response);
        verify(voyageRepository, times(1)).deleteById(1L);
    }

    @Test
    void getTotalTicketNumbersSold_ShouldReturnTotalTicketNumbersSold() {
        Ticket ticket = new Ticket();
        voyage.setTicketList(Arrays.asList(ticket, ticket));
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));

        Integer totalTickets = voyageService.getTotalTicketNumbersSold(1L);

        assertEquals(2, totalTickets);
    }

    @Test
    void getTotalEarningsOfVoyage_ShouldReturnTotalEarnings() {
        Ticket ticket = new Ticket();
        ticket.setPrice(BigDecimal.valueOf(50));
        voyage.setTicketList(Arrays.asList(ticket, ticket));
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));

        BigDecimal totalEarnings = voyageService.getTotalEarninsgOfVoyage(1L);

        assertEquals(BigDecimal.valueOf(100), totalEarnings);
    }

    @Test
    void getVoyagesByCityTravelTypeDateTime_ShouldReturnVoyageResponseList() {
        List<Voyage> voyages = List.of(voyage);
        when(voyageRepository.filterVoyagesByCityTravelTypeDateTime(anyString(), anyString(), anyString(), any(LocalDateTime.class)))
                .thenReturn(voyages);

        List<VoyageResponse> response = voyageService.getVoyagesByCityTravelTypeDateTime("Istanbul", "Ankara", "BUS", "2024-09-01 10:00");

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void getExactVoyage_ShouldReturnVoyage() {
        when(voyageRepository.findVoyageByOriginCityAndDestinationCityAndTravelTypeAndVoyageDateTime(anyString(), anyString(), any(TravelType.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(voyage));

        Voyage result = voyageService.getExactVoyage(bookingRequest);

        assertNotNull(result);
        assertEquals(voyage.getOriginCity(), result.getOriginCity());
    }

    @Test
    void findById_ShouldReturnVoyage() {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));

        Optional<Voyage> result = voyageService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(voyage.getOriginCity(), result.get().getOriginCity());
    }
}
