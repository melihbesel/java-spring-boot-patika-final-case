package com.patika.ticketservice.dto.request;

import com.patika.ticketservice.model.enums.TravelType;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoyageRequest {

    private String originCity;
    private String destinationCity;
    private String voyageDateTime;
    private TravelType travelType;
    private BigDecimal price;
}
