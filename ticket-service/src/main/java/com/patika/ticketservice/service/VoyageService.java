package com.patika.ticketservice.service;

import com.patika.ticketservice.converter.VoyageConverter;
import com.patika.ticketservice.dto.request.BookingRequest;
import com.patika.ticketservice.dto.request.VoyageRequest;
import com.patika.ticketservice.dto.response.VoyageResponse;
import com.patika.ticketservice.exception.TicketException;
import com.patika.ticketservice.model.Ticket;
import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.model.enums.TravelType;
import com.patika.ticketservice.model.enums.VoyageStatus;
import com.patika.ticketservice.producer.KafkaProducer;
import com.patika.ticketservice.repository.VoyageRepository;
import com.patika.ticketservice.util.Constants;
import com.patika.ticketservice.util.DateTimeStringConvertor;
import com.patika.ticketservice.util.LoggerHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

@Slf4j
@RequiredArgsConstructor
@Service
public class VoyageService {

    private final VoyageRepository voyageRepository;
    private final KafkaProducer kafkaProducer;

    @CacheEvict(cacheNames = "getVoyagesByCityTravelTypeDateTime", allEntries = true)
    public VoyageResponse createVoyage(VoyageRequest voyageRequest) {

        Voyage voyage = new Voyage();
        prepareVoyage(voyageRequest, voyage);

        setAvailableSeatForVoyage(voyage);
        voyageRepository.save(voyage);

        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageService -->createVoyage()--> voyage has been saved to voyageRepository.");
        kafkaProducer.sendLog("VoyageService -->createVoyage()--> voyage has been saved to voyageRepository.");

        return VoyageConverter.convertToVoyageResponse(voyage);
    }

    private static void prepareVoyage(VoyageRequest voyageRequest, Voyage voyage) {
        voyage.setOriginCity(voyageRequest.getOriginCity());
        voyage.setDestinationCity(voyageRequest.getDestinationCity());
        voyage.setVoyageDateTime(DateTimeStringConvertor.convertStringToLocalDateTime(voyageRequest.getVoyageDateTime()));
        voyage.setTravelType(voyageRequest.getTravelType());
        voyage.setPrice(voyageRequest.getPrice());
    }

    private void setAvailableSeatForVoyage(Voyage voyage) {
        if (voyage.getTravelType().equals(TravelType.BUS)){
            voyage.setAvailableSeat(Constants.MAX_AVAILABLE_SEATS_FOR_BUS);
        } else {
            voyage.setAvailableSeat(Constants.MAX_AVAILABLE_SEATS_FOR_PLANE);
        }
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageService -->setAvailableSeatForVoyage()--> voyage available seat has been set to initial value.");
        kafkaProducer.sendLog("VoyageService -->setAvailableSeatForVoyage()--> voyage available seat has been set to initial value.");
    }

    public VoyageResponse deactivateVoyage(Long id) {

       Voyage voyage = voyageRepository.findById(id)
               .orElseThrow(() -> new TicketException("There is no such voyage. Check voyage id."));

       voyage.setVoyageStatus(VoyageStatus.PASSIVE);
       voyageRepository.save(voyage);

        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageService -->deactivateVoyage()--> voyageStatus has been set to PASSIVE and saved to voyageRepository.");
        kafkaProducer.sendLog("VoyageService -->deactivateVoyage()--> voyageStatus has been set to PASSIVE and saved to voyageRepository.");

       return VoyageConverter.convertToVoyageResponse(voyage);
    }

    public VoyageResponse deleteVoyage(Long id) {

        Voyage voyage = voyageRepository.findById(id)
                .orElseThrow(() -> new TicketException("There is no such voyage. Check voyage id."));

        voyageRepository.deleteById(id);

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageService -->deleteVoyage()--> voyage deleted by ID from voyageRepository.");

        return VoyageConverter.convertToVoyageResponse(voyage);
    }

    public Integer getTotalTicketNumbersSold(Long id) {
        Voyage voyage = voyageRepository.findById(id)
                .orElseThrow(() -> new TicketException("There is no such voyage. Check voyage id."));

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageService -->getTotalTicketNumbersSold()--> voyage's sold tickets number received from voyage.");

        return voyage.getTicketList().size();
    }

    public BigDecimal getTotalEarninsgOfVoyage(Long id) {
        Voyage voyage = voyageRepository.findById(id)
                .orElseThrow(() -> new TicketException("There is no such voyage. Check voyage id."));

        BigDecimal price = BigDecimal.ZERO;
        for (Ticket element : voyage.getTicketList()) {
            price = price.add(element.getPrice());
        }

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageService -->getTotalEarninsgOfVoyage()--> Total earnings of voyage calculated.");

        return price;
    }

    @Cacheable(value = "getVoyagesByCityTravelTypeDateTime", cacheNames = "getVoyagesByCityTravelTypeDateTime")
    public List<VoyageResponse> getVoyagesByCityTravelTypeDateTime(String originCity, String destinationCity, String travelType, String voyageDateTime) {

        if (Objects.isNull(voyageDateTime)){

            List<Voyage> voyages = voyageRepository
                    .filterVoyagesByCityTravelTypeDateTime(originCity,destinationCity,travelType,
                    DateTimeStringConvertor.formatLocalDateTime(LocalDateTime.now()));

            return VoyageConverter.convertToVoyageResponseList(voyages);
        }

        List<Voyage> list = voyageRepository
                .filterVoyagesByCityTravelTypeDateTime(originCity,destinationCity,travelType,
                DateTimeStringConvertor.convertStringToLocalDateTime(voyageDateTime));

        log.info("getVoyagesByCityTravelTypeDateTime db den getirildi.");
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageService -->getVoyagesByCityTravelTypeDateTime()--> Filtered voyages received from voyageRepository.");
        kafkaProducer.sendLog("VoyageService -->getVoyagesByCityTravelTypeDateTime()--> Filtered voyages received from voyageRepository.");

        return VoyageConverter.convertToVoyageResponseList(list);
    }

    public Voyage getExactVoyage(BookingRequest bookingRequest){

        var voyage = voyageRepository.findVoyageByOriginCityAndDestinationCityAndTravelTypeAndVoyageDateTime(
                bookingRequest.getBookingOriginCity(),
                bookingRequest.getBookingDestinationCity(),
                bookingRequest.getBookingTravelType(),
                DateTimeStringConvertor.convertStringToLocalDateTime(bookingRequest.getBookingVoyageDateTime()))
                .orElseThrow(() -> new TicketException("There is no such voyage. Check voyage id."));

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageService -->getExactVoyage()--> voyage received by bookingRequest information from voyageRepository.");
        kafkaProducer.sendLog("VoyageService -->getExactVoyage()--> voyage received by bookingRequest information from voyageRepository.");

        return voyage;
    }

    public Optional<Voyage> findById(Long voyageId) {
        return voyageRepository.findById(voyageId);
    }
}
