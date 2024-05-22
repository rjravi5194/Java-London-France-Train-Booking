package com.train.london.france.booking.repository;

import com.train.london.france.booking.model.PaymentDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetailsDao, UUID> {
}
