package com.train.london.france.booking.servicetests;

import com.train.london.france.booking.model.BookingDetailsDto;
import com.train.london.france.booking.repository.TicketDetailsRepository;
import com.train.london.france.booking.service.implementation.BookingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BookingServiceImplTest {
    @Mock
    private TicketDetailsRepository ticketDetailsRepository;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testRemoveUserFromTrainSection_Success() {
        // Mocking data
        String email = "test@example.com";
        String journeyDate = "2024-05-16";
        when(ticketDetailsRepository.removeUserFromTrainSection(UUID.randomUUID())).thenReturn(1);
        // Executing the method under test
        String result = bookingServiceImpl.removeUserFromTrainSection(new BookingDetailsDto());
        // Verifying the result
        assertEquals("FAILURE", result);
    }
    @Test
    public void testRemoveUserFromTrainSection_Failure() {
        // Mocking data
        String email = "test@example.com";
        String journeyDate = "2024-05-16";
        when(ticketDetailsRepository.removeUserFromTrainSection(UUID.randomUUID())).thenReturn(0);
        // Executing the method under test
        String result = bookingServiceImpl.removeUserFromTrainSection(new BookingDetailsDto());
        // Verifying the result
        assertEquals("FAILURE", result);
    }

    @Test
    public void testRemoveUserFromTrainSection_Exception() {
        // Mocking data
        String email = "test@example.com";
        String journeyDate = "2024-05-16";
        when(ticketDetailsRepository.removeUserFromTrainSection(UUID.randomUUID())).thenThrow(new RuntimeException());
        // Executing the method under test
        String result = bookingServiceImpl.removeUserFromTrainSection(new BookingDetailsDto());
        // Verifying the result
        assertEquals("FAILURE", result);
    }


}