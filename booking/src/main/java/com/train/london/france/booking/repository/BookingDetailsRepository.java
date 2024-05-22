package com.train.london.france.booking.repository;

import com.train.london.france.booking.model.BookingDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetailsDao, UUID> {

}
