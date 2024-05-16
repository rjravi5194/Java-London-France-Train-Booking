package com.train.london.france.booking.model;

import com.train.london.france.booking.enums.BookingStatus;
import com.train.london.france.booking.enums.SeatTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDto {

    private String bookingId;
    private String bookingDate;
    private String journeyDate;
    private String journeyTime;
    private String startPlace;
    private String endPlace;
    private BookingStatus bookingStatus;
    private int seatNumber;
    private SeatTypes seatType;
    private String sectionType;
    private String firstName;
    private String lastName;
    private String email;
    private int pricePaid;
}
