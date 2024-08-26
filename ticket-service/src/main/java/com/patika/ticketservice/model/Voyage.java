package com.patika.ticketservice.model;

import com.patika.ticketservice.model.enums.TravelType;
import com.patika.ticketservice.model.enums.VoyageStatus;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voyages")
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "origin_city", length = 20)
    private String originCity;

    @Column(name = "destination_city", length = 20)
    private String destinationCity;

    @Column(name = "voyage_date_time")
    private LocalDateTime voyageDateTime;

    @Column(name = "available_seat")
    private Integer availableSeat;

    @Column(name = "travel_type")
    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "voyage_status")
    @Enumerated(EnumType.STRING)
    private VoyageStatus voyageStatus;

    @OneToMany(mappedBy = "voyage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> ticketList;

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", originCity='" + originCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", voyageDateTime=" + voyageDateTime +
                ", availableSeat=" + availableSeat +
                ", travelType=" + travelType +
                ", price=" + price +
                ", voyageStatus=" + voyageStatus +
                '}';
    }

    public Voyage() {
        setVoyageStatus(VoyageStatus.ACTIVE);
    }

}
