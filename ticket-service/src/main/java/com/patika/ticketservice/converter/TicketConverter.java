package com.patika.ticketservice.converter;

import com.patika.ticketservice.dto.request.TicketRequest;
import com.patika.ticketservice.dto.response.TicketResponse;
import com.patika.ticketservice.model.Ticket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketConverter {

    public static Ticket convertToTicket(TicketRequest ticketRequest) {
        return Ticket.builder()
                .citizenshipNumber(ticketRequest.getCitizenshipNumber())
                .passengerFirstName(ticketRequest.getPassengerFirstName())
                .passengerMiddleName(ticketRequest.getPassengerMiddleName())
                .passengerLastName(ticketRequest.getPassengerLastName())
                .gender(ticketRequest.getGender())
                .build();
    }

    public static List<Ticket> convertToTicketList(List<TicketRequest> ticketRequests) {
        return ticketRequests.stream().map(TicketConverter::convertToTicket).toList();
    }

    public static TicketResponse convertToTicketResponse(Ticket ticket) {
        /*
            private String voyageOriginCity;
    private String voyageDestinationCity;
    private LocalDateTime voyageDateTime;
    private TravelType voyageTravelType;
         */
        return TicketResponse.builder()
                .citizenshipNumber(ticket.getCitizenshipNumber())
                .passengerFirstName(ticket.getPassengerFirstName())
                .passengerMiddleName(ticket.getPassengerMiddleName())
                .passengerLastName(ticket.getPassengerLastName())
                .gender(ticket.getGender())
                .price(ticket.getPrice())
                .voyageOriginCity(ticket.getVoyage().getOriginCity())
                .voyageDestinationCity(ticket.getVoyage().getDestinationCity())
                .voyageDateTime(ticket.getVoyage().getVoyageDateTime())
                .voyageTravelType(ticket.getVoyage().getTravelType())
                .build();
    }


    public static List<TicketResponse> convertToTicketResponseList(List<Ticket> tickets) {
        return tickets.stream().map(TicketConverter::convertToTicketResponse).toList();
    }

}
