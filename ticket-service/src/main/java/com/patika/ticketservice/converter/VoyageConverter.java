package com.patika.ticketservice.converter;

import com.patika.ticketservice.dto.response.VoyageResponse;
import com.patika.ticketservice.model.Voyage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoyageConverter {

    public static VoyageResponse convertToVoyageResponse(Voyage voyage) {
        return VoyageResponse.builder()
                .originCity(voyage.getOriginCity())
                .destinationCity(voyage.getDestinationCity())
                .voyageDateTime(voyage.getVoyageDateTime().toString())
                .availableSeat(voyage.getAvailableSeat())
                .travelType(voyage.getTravelType())
                .price(voyage.getPrice())
                .build();
    }

    public static List<VoyageResponse> convertToVoyageResponseList(List<Voyage> voyages) {
        return voyages.stream().map(VoyageConverter::convertToVoyageResponse).toList();
    }

}
