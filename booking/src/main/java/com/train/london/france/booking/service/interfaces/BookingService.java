package com.train.london.france.booking.service.interfaces;

import com.train.london.france.booking.model.BookingDetailsDto;
import com.train.london.france.booking.model.CustomerDetailsDao;
import com.train.london.france.booking.model.SectionDetailsDto;
import com.train.london.france.booking.model.TicketDetailsDao;

import java.util.List;

public interface BookingService {
    BookingDetailsDto bookTicket(TicketDetailsDao ticketDetailsDao);

    List<SectionDetailsDto> ViewTrainSectionDetails(String sectionType, String journeyDate);

    String removeUserFromTrainSection(BookingDetailsDto bookingDetailsDto);

    BookingDetailsDto modifyUserTicket(CustomerDetailsDao customerDetailsDao, String journeyDate,int seatNumber,String sectionType);
}
