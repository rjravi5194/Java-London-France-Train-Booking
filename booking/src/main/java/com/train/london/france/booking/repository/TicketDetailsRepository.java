package com.train.london.france.booking.repository;

import com.train.london.france.booking.model.TicketDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetailsDao, UUID> {

    @Query(value =" select td.* from ticket_details_dao  td join booking_details_dao bd on td.booking_id=bd.booking_id " +
            " where bd.journey_date=:journeyDate and td.section_type=:sectionType" ,nativeQuery = true)
    List<TicketDetailsDao> viewTrainSectionDetails(String sectionType, String journeyDate);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value =" Update ticket_details_dao td " +
            "set td.booking_status='CANCELLED' " +
            "where td.booking_status='CONFIRMED' and td.booking_id in( " +
            "Select td.booking_id from ticket_details_dao td " +
            "join customer_details_dao cd on td.customer_id=cd.customer_id " +
            "join booking_details_dao bd on td.booking_id=bd.booking_id " +
            "where cd.email=:email and bd.journey_date=:journeyDate ) " ,nativeQuery = true)
    int removeUserFromTrainSection(String email, String journeyDate);

    @Query(value =" select td.* from ticket_details_dao  td join booking_details_dao bd on td.booking_id=bd.booking_id " +
            " join customer_details_dao cd on td.customer_id=cd.customer_id " +
            " where cd.email=:email and bd.journey_date=:journeyDate" ,nativeQuery = true)
    TicketDetailsDao findByEmailAndJourneyDate(String email, String journeyDate);

    @Query(value =" select td.* from ticket_details_dao  td join booking_details_dao bd on td.booking_id=bd.booking_id " +
            " where bd.journey_date=:journeyDate and td.section_type=:sectionType and td.seat_type= :seatType" ,nativeQuery = true)
    List<TicketDetailsDao> viewBookedSeats(String sectionType, String journeyDate, String seatType);
}
