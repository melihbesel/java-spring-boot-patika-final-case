package com.patika.ticketservice.controller;

import com.patika.ticketservice.dto.request.VoyageRequest;
import com.patika.ticketservice.dto.response.GenericResponse;
import com.patika.ticketservice.dto.response.VoyageResponse;
import com.patika.ticketservice.service.VoyageService;
import com.patika.ticketservice.util.LoggerHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/voyages")
public class VoyageController {

    private final VoyageService voyageService;

    @PostMapping
    public GenericResponse<VoyageResponse> createVoyage(@RequestBody VoyageRequest voyageRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->createVoyage()--> voyageRequest has been sent to VoyageService createVoyage().");

        return GenericResponse.success(voyageService.createVoyage(voyageRequest), HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public GenericResponse<VoyageResponse> deactivateVoyage(@PathVariable Long id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->deactivateVoyage()--> voyage ID has been sent to VoyageService deactivateVoyage().");

        return GenericResponse.success(voyageService.deactivateVoyage(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public GenericResponse<VoyageResponse> deleteVoyage(@PathVariable Long id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->deleteVoyage()--> voyage ID has been sent to VoyageService deleteVoyage().");

        return GenericResponse.success(voyageService.deleteVoyage(id), HttpStatus.OK);
    }

    @GetMapping("/totalTicketsSold/{id}")
    public GenericResponse<Integer> getTotalTicketNumbersSold(@PathVariable Long id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->getTotalTicketNumbersSold()--> voyage ID has been sent to VoyageService getTotalTicketNumbersSold().");

        return GenericResponse.success(voyageService.getTotalTicketNumbersSold(id), HttpStatus.OK);
    }

    @GetMapping("/totalEarnings/{id}")
    public GenericResponse<BigDecimal> getTotalEarninsgOfVoyage(@PathVariable Long id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->getTotalEarninsgOfVoyage()--> voyage ID has been sent to VoyageService getTotalEarninsgOfVoyage().");

        return GenericResponse.success(voyageService.getTotalEarninsgOfVoyage(id), HttpStatus.OK);
    }

    @GetMapping
    public GenericResponse<List<VoyageResponse>> getVoyages(@RequestParam String originCity,
                                                            @RequestParam String destinationCity,
                                                            @RequestParam String travelType,
                                                            @RequestParam(required = false) String voyageDateTime) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->getVoyages()--> originCity, destinationCity, travelType, voyageDateTime has been sent to VoyageService getVoyages().");

        return GenericResponse.success(voyageService.getVoyagesByCityTravelTypeDateTime(originCity, destinationCity, travelType, voyageDateTime), HttpStatus.OK);
    }
}
