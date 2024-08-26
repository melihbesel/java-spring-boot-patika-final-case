package com.patika.ticketservice.model;

import com.patika.ticketservice.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "citizenship_number", length = 11)
    private String citizenshipNumber;

    @Column(name = "passenger_first_name", length = 30)
    private String passengerFirstName;

    @Column(name = "passenger_middle_name", length = 30)
    private String passengerMiddleName;

    @Column(name = "passenger_last_name", length = 30)
    private String passengerLastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "voyage_id", referencedColumnName = "id", nullable = true)
    private Voyage voyage;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id", nullable = true)
    private Booking booking;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", citizenshipNumber='" + citizenshipNumber + '\'' +
                ", passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerMiddleName='" + passengerMiddleName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", gender=" + gender +
                ", price=" + price +
                ", voyage=" + voyage.toString() +
                ", booking=" + booking.toString() +
                '}';
    }
}
