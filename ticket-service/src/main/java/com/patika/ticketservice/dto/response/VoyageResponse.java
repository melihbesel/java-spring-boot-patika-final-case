package com.patika.ticketservice.dto.response;

import com.patika.ticketservice.model.enums.TravelType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoyageResponse implements Serializable {

    private String originCity;
    private String destinationCity;
    private String voyageDateTime;
    private Integer availableSeat;
    private TravelType travelType;
    private BigDecimal price;
}
