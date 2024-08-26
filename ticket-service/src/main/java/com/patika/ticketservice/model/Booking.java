package com.patika.ticketservice.model;

import com.patika.ticketservice.client.payment.service.dto.response.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date_time")
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column(name = "booking_total_price")
    private BigDecimal bookingTotalPrice;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ticket> ticketList;

    public void setBookingTotalPrice() {
        BigDecimal bookingTotalPrice = BigDecimal.ZERO;
        for (Ticket element : ticketList) {
            bookingTotalPrice = bookingTotalPrice.add(element.getPrice());
        }
        this.bookingTotalPrice = bookingTotalPrice;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", creationDateTime=" + creationDateTime +
                ", bookingTotalPrice=" + bookingTotalPrice +
                ", paymentStatus=" + paymentStatus +
                ", isActive=" + isActive +
                ", userId=" + userId +
                '}';
    }
}
