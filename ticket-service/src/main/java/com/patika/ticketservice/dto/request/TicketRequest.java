package com.patika.ticketservice.dto.request;

import com.patika.ticketservice.model.enums.Gender;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketRequest {

    private String citizenshipNumber;
    private String passengerFirstName;
    private String passengerMiddleName;
    private String passengerLastName;
    private Gender gender;
}
