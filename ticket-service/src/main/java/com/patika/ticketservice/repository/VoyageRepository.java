package com.patika.ticketservice.repository;

import com.patika.ticketservice.model.Voyage;
import com.patika.ticketservice.model.enums.TravelType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {

    @Query(value = "SELECT *\n" +
            "FROM ticketdb.public.voyages v \n" +
            "where \n" +
            "lower(origin_city)  = lower(:originCity) and lower(destination_city)  = lower(:destinationCity)\n" +
            "and (travel_type = coalesce (:travelType,'PLANE') or travel_type = coalesce (:travelType,'BUS'))\n" +
            "and ( cast(voyage_date_time as DATE) = :voyageDateTime)" +
            "and (available_seat > 0)" +
            "and (voyage_status = 'ACTIVE');", nativeQuery = true)
    List<Voyage> filterVoyagesByCityTravelTypeDateTime(@Param("originCity") String originCity,
                                                       @Param("destinationCity") String destinationCity,
                                                       @Param("travelType") String travelType,
                                                       @Param("voyageDateTime") LocalDateTime voyageDateTime);

    Optional<Voyage> findVoyageByOriginCityAndDestinationCityAndTravelTypeAndVoyageDateTime(
            String originCity, String destinationCity, TravelType travelType, LocalDateTime voyageDateTime);

}
