package com.patika.ticketservice.service;

import com.patika.ticketservice.client.payment.service.PaymentClientService;
import com.patika.ticketservice.client.payment.service.dto.request.PaymentRequest;
import com.patika.ticketservice.client.payment.service.dto.response.PaymentResponse;
import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentStatus;
import com.patika.ticketservice.client.user.dto.response.UserResponse;
import com.patika.ticketservice.client.user.service.UserClientService;
import com.patika.ticketservice.converter.BookingConverter;
import com.patika.ticketservice.converter.TicketConverter;
import com.patika.ticketservice.dto.producer.NotificationRequest;
import com.patika.ticketservice.dto.producer.enums.NotificationType;
import com.patika.ticketservice.dto.request.BookingRequest;
import com.patika.ticketservice.dto.request.TicketRequest;
import com.patika.ticketservice.dto.response.BookingResponse;
import com.patika.ticketservice.exception.TicketException;
import com.patika.ticketservice.model.Booking;
import com.patika.ticketservice.model.Ticket;
import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.model.enums.Gender;
import com.patika.ticketservice.model.enums.UserType;
import com.patika.ticketservice.producer.KafkaProducer;
import com.patika.ticketservice.producer.RabbitMqProducer;
import com.patika.ticketservice.repository.BookingRepository;
import com.patika.ticketservice.util.Constants;
import com.patika.ticketservice.util.LoggerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VoyageService voyageService;
    private final PaymentClientService paymentClientService;
    private final UserClientService userClientService;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;

    public BookingResponse createBooking(BookingRequest bookingRequest) {

        checkBookingTicketListSize(bookingRequest);
        Voyage requestedVoyage = voyageService.getExactVoyage(bookingRequest);

        LoggerHandler.getLogger().log(Level.INFO,
                "BookingService --> createBooking()--> voyage received by voyageService-->getExactVoyage().");
        kafkaProducer.sendLog("BookingService --> createBooking()--> voyage received by voyageService-->getExactVoyage().");

        checkVoyageHasAvaliableSeats(requestedVoyage, bookingRequest);

        UserResponse user = userClientService.getUserById(bookingRequest.getBookingUserId());

        checkBookingConstraints(bookingRequest, user, requestedVoyage);

        Booking booking = new Booking();

        List<Ticket> ticketList = TicketConverter.convertToTicketList(bookingRequest.getBookingTicketRequestList());

        processBooking(requestedVoyage, user, booking, ticketList);

        bookingRepository.save(booking);
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingService -->createBooking()--> bookingRequest saved to bookingRepository as Booking.");
        kafkaProducer.sendLog("BookingService -->createBooking()--> bookingRequest saved to bookingRepository as Booking.");

        return BookingConverter.convertToBookingResponse(booking, user);
    }

    public BookingResponse getBookingById(Long bookingId) {

        UserResponse user = userClientService.getUserById(bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new TicketException("Booking not found"));

        LoggerHandler.getLogger().log(Level.INFO,
                "BookingService --> getBookingById()--> booking received by ID from bookingRepository.");
        kafkaProducer.sendLog("BookingService --> getBookingById()--> booking received by ID from bookingRepository.");

        return BookingConverter.convertToBookingResponse(booking, user);
    }

    public void changeBookingPaymentStatus(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new TicketException("booking not found"));

        booking.setPaymentStatus(PaymentStatus.NOT_PAID);

        LoggerHandler.getLogger().log(Level.INFO,
                "BookingService -->changeBookingPaymentStatus()--> booking payment status has been changed.");
        kafkaProducer.sendLog("BookingService -->changeBookingPaymentStatus()--> booking payment status has been changed.");

        bookingRepository.save(booking);
    }


    private void checkBookingConstraints(BookingRequest bookingRequest, UserResponse user, Voyage voyage) {

        List<Booking> bookingList = bookingRepository.findByUserId(user.getUserId());

        long userTicketCount = (Objects.isNull(bookingList)) ? 0L : bookingList.stream()
                .flatMap(booking -> booking.getTicketList()
                        .stream()
                        .filter(ticket -> ticket.getVoyage().getId().equals(user.getUserId()))).count();

        if (user.getUserType().equals(UserType.CORPORATE)) {
            if (bookingRequest.getBookingTicketRequestList().size() + userTicketCount > Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_CORPORATE_PASSENGER) {

                LoggerHandler.getLogger().log(Level.WARNING,
                        "BookingService -->checkBookingConstraints()-->" +
                                "bookingRequest checked for Ticket Limit Size(" +
                                Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_CORPORATE_PASSENGER +
                                ") for CORPORATE passenger.");

                throw new TicketException("Corporate passenger can not book more than " +
                        Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_CORPORATE_PASSENGER +
                        " tickets for one booking.");
            }
        }

        if (user.getUserType().equals(UserType.INDIVIDUAL)) {
            if (bookingRequest.getBookingTicketRequestList().size() + userTicketCount > Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER) {

                LoggerHandler.getLogger().log(Level.WARNING,
                        "BookingService -->checkBookingConstraints()-->" +
                                "bookingRequest checked for Ticket Limit Size(" +
                                Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                                ") for INDIVIDUAL passenger.");

                throw new TicketException("Individual passenger can not book more than "
                        + Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                        " tickets for one booking.");
            }
            Integer malePassengerInTicketCounter = 0;

            for (TicketRequest element : bookingRequest.getBookingTicketRequestList()) {
                if (element.getGender().equals(Gender.MALE)) {
                    malePassengerInTicketCounter++;
                }
                if (malePassengerInTicketCounter > Constants.MAX_ALLOWED_MALE_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER) {

                    LoggerHandler.getLogger().log(Level.WARNING, "BookingService -->checkBookingConstraints()-->" +
                            "bookingRequest checked for Male Limit Size(" +
                            Constants.MAX_ALLOWED_MALE_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                            ") for INDIVIDUAL passenger.");

                    throw new TicketException("Individual passenger can not book for more than " +
                            Constants.MAX_ALLOWED_MALE_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                            " male passengers.");
                }
            }
        }
    }


    private void processBooking(Voyage requestedVoyage, UserResponse user, Booking booking, List<Ticket> ticketList) {

        ticketList.stream().forEach(ticket -> {
            ticket.setBooking(booking);
            ticket.setVoyage(requestedVoyage);
            ticket.setPrice(requestedVoyage.getPrice());
        });

        requestedVoyage.setAvailableSeat(requestedVoyage.getAvailableSeat() - ticketList.size());
        booking.setTicketList(ticketList);
        booking.setBookingTotalPrice();
        booking.setUserId(user.getUserId());
    }


    private static void checkBookingTicketListSize(BookingRequest bookingRequest) {
        if (bookingRequest.getBookingTicketRequestList() == null || !(bookingRequest.getBookingTicketRequestList().size() > 0)) {
            throw new TicketException("Null or empty ticket list to create booking.");
        }
    }

    private static void checkVoyageHasAvaliableSeats(Voyage voyage, BookingRequest bookingRequest) {

        if (voyage.getAvailableSeat() < bookingRequest.getBookingTicketRequestList().size()) {

            throw new TicketException("Voyage has no avaliable seat for booking.");
        }
    }

    public PaymentResponse payment(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setBookingId(paymentRequest.getBookingId());
        paymentResponse.setAmount(paymentRequest.getAmount());
        paymentResponse.setPaymentType(paymentRequest.getPaymentType());
        paymentResponse.setCreatedDateTime(LocalDateTime.now());
        paymentResponse.setPaymentStatus(PaymentStatus.NOT_PAID);

        Long bookingId =  paymentRequest.getBookingId();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new TicketException("Booking not found"));

        BigDecimal totalTicketsPrice = booking.getTicketList().stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Ticket> bookingTickets = booking.getTicketList();
        UserResponse user = userClientService.getUserById(booking.getUserId());

        if (totalTicketsPrice.compareTo(paymentRequest.getAmount()) <= 0) {

            paymentResponse = paymentClientService.createPayment(paymentRequest);

            booking.setPaymentStatus(paymentResponse.getPaymentStatus());
            bookingRepository.save(booking);

            String message = "Thank you for booking, Ticket information is the below.\n";
            for (Ticket ticket : bookingTickets) {
                message += "\n" + ticket.toString();
            }
            rabbitMqProducer.sendNotification(new NotificationRequest(message, NotificationType.SMS, user.getTelephoneNumber()));
            rabbitMqProducer.sendNotification(new NotificationRequest(message, NotificationType.PUSH, user.getReceiverId()));
            rabbitMqProducer.sendNotification(new NotificationRequest(message, NotificationType.MAIL, user.getEmail()));
        }

        return paymentResponse;
    }

}
