package com.patika.ticketservice.dto.response;

import com.patika.ticketservice.model.enums.Gender;
import com.patika.ticketservice.model.enums.TravelType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketResponse {
    private String citizenshipNumber;
    private String passengerFirstName;
    private String passengerMiddleName;
    private String passengerLastName;
    private Gender gender;
    private BigDecimal price;
    private String voyageOriginCity;
    private String voyageDestinationCity;
    private LocalDateTime voyageDateTime;
    private TravelType voyageTravelType;
}
