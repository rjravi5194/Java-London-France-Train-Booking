package com.train.london.france.booking.model;

import com.train.london.france.booking.enums.BookingStatus;
import com.train.london.france.booking.enums.SeatTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetailsDao {

    @Id
    @GeneratedValue
    private UUID ticketId;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private int seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatTypes seatType;
    private String sectionType;
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingDetailsDao bookingDetailsDao;
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerDetailsDao customerDetailsDao;
    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentDetailsDao paymentDetailsDao;

}
