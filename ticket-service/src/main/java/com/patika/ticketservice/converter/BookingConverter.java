package com.patika.ticketservice.converter;

import com.patika.ticketservice.client.user.dto.response.UserResponse;
import com.patika.ticketservice.dto.response.BookingResponse;
import com.patika.ticketservice.dto.response.TicketResponse;
import com.patika.ticketservice.model.Booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingConverter {

    public static BookingResponse convertToBookingResponse(Booking booking, UserResponse user) {
        List<TicketResponse> ticketResponseList = booking.getTicketList().stream()
                .map(TicketConverter::convertToTicketResponse)
                .collect(Collectors.toList());

        return BookingResponse.builder()
                .id(booking.getId())
                .passengerTelephoneNumber(user.getTelephoneNumber())
                .creationDateTime(booking.getCreationDateTime())
                .bookingTotalPrice(booking.getBookingTotalPrice())
                .ticketResponseList(ticketResponseList)
                .build();
    }



}
