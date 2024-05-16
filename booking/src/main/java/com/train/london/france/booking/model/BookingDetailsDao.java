package com.train.london.france.booking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class BookingDetailsDao {

    @Id
    @GeneratedValue
    private UUID bookingId;
    private String bookingDate;
    private String journeyDate;
    private String journeyTime;
    private String startPlace;
    private String endPlace;

}
