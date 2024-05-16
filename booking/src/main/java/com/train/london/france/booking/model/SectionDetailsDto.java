package com.train.london.france.booking.model;

import com.train.london.france.booking.enums.SeatTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionDetailsDto {

    private String sectionType;
    private String journeyDate;
    private int seatNumber;
    private SeatTypes seatType;
    private CustomerDetailsDao customerDetailsDao;

}
