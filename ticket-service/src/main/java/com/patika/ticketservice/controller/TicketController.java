package com.patika.ticketservice.controller;

import com.patika.ticketservice.dto.request.TicketRequest;
import com.patika.ticketservice.dto.response.GenericResponse;
import com.patika.ticketservice.dto.response.TicketResponse;
import com.patika.ticketservice.service.TicketService;
import com.patika.ticketservice.util.LoggerHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{voyageId}")
    public GenericResponse<TicketResponse> createTicket(@PathVariable Long voyageId, @RequestBody TicketRequest ticketRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->createTicket()--> voyage ID and TicketRequest has been sent to TicketService createTicket()");

        return GenericResponse.success(ticketService.createTicket(voyageId, ticketRequest), HttpStatus.OK);
    }

    @PutMapping("{ticketId}")
    public GenericResponse<TicketResponse> updateTicket(@PathVariable Long ticketId, @RequestBody TicketRequest ticketRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->updateTicket()--> ticket ID and TicketRequest has been sent to TicketService updateTicket()");

        return GenericResponse.success(ticketService.updateTicket(ticketId, ticketRequest), HttpStatus.OK);
    }

    @GetMapping("/byId/{ticketId}")
    public GenericResponse<TicketResponse> getTicketById(@PathVariable Long ticketId) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->getTicketById()--> ticket ID has been sent to TicketService getTicketById()");

        return GenericResponse.success(ticketService.getTicketById(ticketId), HttpStatus.OK);
    }

    @GetMapping("/byEmail/{email}")
    public GenericResponse<List<TicketResponse>> getAllByEmail(@PathVariable String email) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->getAllByEmail()--> email has been sent to TicketService getAllByEmail()");

        return GenericResponse.success(ticketService.getAllByEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("/{ticketId}")
    public GenericResponse<TicketResponse> deleteTicketById(@PathVariable Long ticketId) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->deleteTicketById()--> ticket ID has been sent to TicketService deleteTicketById()");

        return GenericResponse.success(ticketService.deleteTicketById(ticketId), HttpStatus.OK);
    }
}
