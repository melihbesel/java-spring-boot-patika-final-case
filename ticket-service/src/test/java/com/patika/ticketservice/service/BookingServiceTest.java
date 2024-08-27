package com.patika.ticketservice.service;

import com.patika.ticketservice.client.payment.service.PaymentClientService;
import com.patika.ticketservice.client.payment.service.dto.request.PaymentRequest;
import com.patika.ticketservice.client.payment.service.dto.response.PaymentResponse;
import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentStatus;
import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentType;
import com.patika.ticketservice.client.user.dto.response.UserResponse;
import com.patika.ticketservice.client.user.service.UserClientService;
import com.patika.ticketservice.dto.request.BookingRequest;
import com.patika.ticketservice.dto.request.TicketRequest;
import com.patika.ticketservice.dto.response.BookingResponse;
import com.patika.ticketservice.exception.TicketException;
import com.patika.ticketservice.model.Booking;
import com.patika.ticketservice.model.Ticket;
import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.client.user.dto.response.enums.UserType;
import com.patika.ticketservice.producer.KafkaProducer;
import com.patika.ticketservice.producer.RabbitMqProducer;
import com.patika.ticketservice.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private VoyageService voyageService;

    @Mock
    private PaymentClientService paymentClientService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private BookingRequest createBookingRequest() {
        BookingRequest request = new BookingRequest();
        request.setBookingUserId(1L);
        request.setBookingTicketRequestList(List.of(new TicketRequest()));
        return request;
    }

    private UserResponse createUserResponse() {
        UserResponse user = new UserResponse();
        user.setUserId(1L);
        user.setUserType(UserType.INDIVIDUAL);
        user.setTelephoneNumber("1234567890");
        user.setReceiverId("receiverId");
        user.setEmail("user@example.com");
        return user;
    }

    private Voyage createVoyage() {
        Voyage voyage = new Voyage();
        voyage.setAvailableSeat(10);
        voyage.setPrice(BigDecimal.valueOf(100.0));
        return voyage;
    }

    private Booking createBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setTicketList(List.of(new Ticket()));
        booking.setBookingTotalPrice(BigDecimal.valueOf(100.0));
        return booking;
    }

    private PaymentResponse createPaymentResponse(Long bookingId) {
        PaymentResponse response = new PaymentResponse();
        response.setBookingId(bookingId);
        response.setAmount(BigDecimal.valueOf(100.0));
        response.setPaymentType(PaymentType.CREDIT_CARD);
        response.setCreatedDateTime(LocalDateTime.now());
        response.setPaymentStatus(PaymentStatus.PAID);
        return response;
    }

    @Test
    void createBooking_shouldSaveBookingSuccessfully() {
        // Arrange
        BookingRequest bookingRequest = createBookingRequest();
        Voyage voyage = createVoyage();
        UserResponse userResponse = createUserResponse();
        Booking booking = createBooking();

        when(voyageService.getExactVoyage(any(BookingRequest.class))).thenReturn(voyage);
        when(userClientService.getUserById(anyLong())).thenReturn(userResponse);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);

        // Assert
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(kafkaProducer, times(2)).sendLog(anyString());
        assertNotNull(bookingResponse);
    }

    @Test
    void getBookingById_shouldThrowExceptionWhenBookingNotFound() {
        // Arrange
        Long bookingId = 1L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act & Assert
        TicketException exception = assertThrows(TicketException.class, () -> bookingService.getBookingById(bookingId));
        assertEquals("Booking not found", exception.getMessage());
    }

    @Test
    void changeBookingPaymentStatus_shouldChangeStatusSuccessfully() {
        // Arrange
        Long bookingId = 1L;
        Booking booking = createBooking();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        // Act
        bookingService.changeBookingPaymentStatus(bookingId);

        // Assert
        verify(bookingRepository, times(1)).save(any(Booking.class));
        assertEquals(PaymentStatus.NOT_PAID, booking.getPaymentStatus());
        verify(kafkaProducer, times(1)).sendLog(anyString());
    }

    @Test
    void changeBookingPaymentStatus_shouldThrowExceptionWhenBookingNotFound() {
        // Arrange
        Long bookingId = 1L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act & Assert
        TicketException exception = assertThrows(TicketException.class, () -> bookingService.changeBookingPaymentStatus(bookingId));
        assertEquals("booking not found", exception.getMessage());
    }

    @Test
    void payment_shouldThrowExceptionWhenBookingNotFound() {
        // Arrange
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setBookingId(1L);
        paymentRequest.setAmount(BigDecimal.valueOf(100.0));
        paymentRequest.setPaymentType(PaymentType.CREDIT_CARD);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        TicketException exception = assertThrows(TicketException.class, () -> bookingService.payment(paymentRequest));
        assertEquals("Booking not found", exception.getMessage());
    }

}
