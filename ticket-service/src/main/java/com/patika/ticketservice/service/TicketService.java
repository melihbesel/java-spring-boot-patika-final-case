package com.patika.ticketservice.service;

import com.patika.ticketservice.client.user.dto.response.UserResponse;
import com.patika.ticketservice.client.user.service.UserClientService;
import com.patika.ticketservice.converter.TicketConverter;
import com.patika.ticketservice.dto.request.TicketRequest;
import com.patika.ticketservice.dto.response.TicketResponse;
import com.patika.ticketservice.exception.TicketException;
import com.patika.ticketservice.model.Booking;
import com.patika.ticketservice.model.Ticket;
import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.producer.KafkaProducer;
import com.patika.ticketservice.repository.BookingRepository;
import com.patika.ticketservice.repository.TicketRepository;
import com.patika.ticketservice.util.LoggerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VoyageService voyageService;
    private final UserClientService userClientService;
    private final BookingRepository bookingRepository;
    private final KafkaProducer kafkaProducer;

    public TicketResponse createTicket(Long voyageId, TicketRequest ticketRequest) {

        Voyage voyage = voyageService.findById(voyageId)
                .orElseThrow(() -> new TicketException("There is no such voyage. Check voyage id."));

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketService -->createTicket()--> voyage received by ID from voyageService.");
        kafkaProducer.sendLog("TicketService -->createTicket()--> voyage received by ID from voyageService.");

        Ticket ticket = TicketConverter.convertToTicket(ticketRequest);
        ticket.setPrice(voyage.getPrice());
        ticket.setVoyage(voyage);

        ticketRepository.save(ticket);

        return TicketConverter.convertToTicketResponse(ticket);
    }

    public TicketResponse updateTicket(Long id, TicketRequest ticketRequest) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("There is no such ticket. Check ticket id."));

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketService -->updateTicket()--> ticket received by ID from ticketRepository.");
        kafkaProducer.sendLog("TicketService -->updateTicket()--> ticket received by ID from ticketRepository.");

        ticket.setCitizenshipNumber(ticket.getCitizenshipNumber());
        ticket.setPassengerFirstName(ticketRequest.getPassengerFirstName());
        ticket.setPassengerMiddleName(ticketRequest.getPassengerMiddleName());
        ticket.setPassengerLastName(ticketRequest.getPassengerLastName());
        ticket.setGender(ticketRequest.getGender());

        ticketRepository.save(ticket);

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketService -->updateTicket()--> ticket updated and saved to ticketRepository.");
        kafkaProducer.sendLog("TicketService -->updateTicket()--> ticket updated and saved to ticketRepository.");

        return TicketConverter.convertToTicketResponse(ticket);
    }

    public TicketResponse getTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("There is no such ticket. Check ticket id."));

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketService -->getTicketById()--> ticket received by ID from ticketRepository.");
        kafkaProducer.sendLog("TicketService -->getTicketById()--> ticket received by ID from ticketRepository.");

        return TicketConverter.convertToTicketResponse(ticket);
    }

    public TicketResponse deleteTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("There is no such ticket. Check ticket id."));

        ticketRepository.deleteById(id);

        LoggerHandler.getLogger().log(Level.WARNING,
                "TicketService -->deleteTicketById()--> ticket deleted by ID from ticketRepository.");

        return TicketConverter.convertToTicketResponse(ticket);
    }

    public List<TicketResponse> getAllByEmail(String email) {

        UserResponse user = userClientService.getUserByEmail(email);

        List<Booking> bookingList = bookingRepository.findByUserId(user.getUserId());

        List<Ticket> ticketList = bookingList
                .stream().flatMap(booking -> booking.getTicketList().stream()).toList();

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketService -->getAllByTelephoneNumber()--> ticketList received from user's TicketList.");
        kafkaProducer.sendLog("TicketService -->getAllByTelephoneNumber()--> ticketList received from user's TicketList.");

        return TicketConverter.convertToTicketResponseList(ticketList);
    }
}
