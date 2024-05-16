package com.train.london.france.booking.controllertests;

import com.train.london.france.booking.BookingApplication;
import com.train.london.france.booking.controller.BookingController;
import com.train.london.france.booking.model.BookingDetailsDto;
import com.train.london.france.booking.model.CustomResponse;
import com.train.london.france.booking.model.TicketDetailsDao;
import com.train.london.france.booking.service.interfaces.BookingService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = BookingApplication.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    BookingService bookingService;

    @Mock
    MessageSource messageSource;

    // Add your tests here
    @Test
    public void testBookTicket() throws Exception {
        when(bookingService.bookTicket(new TicketDetailsDao())).thenReturn(null);
        Assertions.assertNotEquals(null, bookingController.bookTicket(new TicketDetailsDao()));
    }

    @Test
    public void testBookTicket2() throws Exception {
        when(bookingService.bookTicket(new TicketDetailsDao())).thenReturn(new BookingDetailsDto());
        Assertions.assertNotEquals(null, bookingController.bookTicket(new TicketDetailsDao()));
    }

    @Test
    public void testBookTicket3() throws Exception {
        when(bookingService.bookTicket(new TicketDetailsDao())).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.bookTicket(new TicketDetailsDao());
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBookTicket4() throws Exception {
        when(bookingService.bookTicket(new TicketDetailsDao())).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.bookTicket(new TicketDetailsDao());
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testBookTicket5() throws Exception {
        when(bookingService.bookTicket(new TicketDetailsDao())).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.bookTicket(new TicketDetailsDao());
        Assertions.assertEquals("SUCCESS", response.getBody().getMessage());
    }

    @Test
    public void testModifyUserTicket() throws Exception {
        when(bookingService.modifyUserTicket(null, "journeyDate")).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.modifyUserTicket(null, "journeyDate");
        Assertions.assertEquals("SUCCESS", response.getBody().getMessage());
    }

    @Test
    public void testModifyUserTicket2() throws Exception {
        when(bookingService.modifyUserTicket(null, "journeyDate")).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.modifyUserTicket(null, "journeyDate");
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testModifyUserTicket3() throws Exception {
        when(bookingService.modifyUserTicket(null, "journeyDate")).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.modifyUserTicket(null, "journeyDate");
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testModifyUserTicket4() throws Exception {
        when(bookingService.modifyUserTicket(null, "journeyDate")).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.modifyUserTicket(null, "journeyDate");
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testModifyUserTicket5() throws Exception {
        when(bookingService.modifyUserTicket(null, "journeyDate")).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.modifyUserTicket(null, "journeyDate");
        Assertions.assertEquals("SUCCESS", response.getBody().getMessage());
    }

    @Test
    public void testModifyUserTicket6() throws Exception {
        when(bookingService.modifyUserTicket(null, "journeyDate")).thenReturn(new BookingDetailsDto());
        ResponseEntity<CustomResponse> response = bookingController.modifyUserTicket(null, "journeyDate");
        Assertions.assertEquals("SUCCESS", response.getBody().getMessage());
    }

    @Test
    public void testViewTrainSectionDetails() throws Exception {
        when(bookingService.ViewTrainSectionDetails("sectionType", "journeyDate")).thenReturn(null);
        Assertions.assertNotEquals(null, bookingController.ViewTrainSectionDetails("sectionType", "journeyDate"));
    }

    @Test
    public void testViewTrainSectionDetails2() throws Exception {
        when(bookingService.ViewTrainSectionDetails("sectionType", "journeyDate")).thenReturn(null);
        ResponseEntity<CustomResponse> response = bookingController.ViewTrainSectionDetails("sectionType", "journeyDate");
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testViewTrainSectionDetails3() throws Exception {
        when(bookingService.ViewTrainSectionDetails("sectionType", "journeyDate")).thenReturn(null);
        ResponseEntity<CustomResponse> response = bookingController.ViewTrainSectionDetails("sectionType", "journeyDate");
        Assertions.assertEquals("FAILURE", response.getBody().getMessage());
    }

    @Test
    public void testViewTrainSectionDetails4() throws Exception {
        when(bookingService.ViewTrainSectionDetails("sectionType", "journeyDate")).thenReturn(null);
        ResponseEntity<CustomResponse> response = bookingController.ViewTrainSectionDetails("sectionType", "journeyDate");
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testViewTrainSectionDetails5() throws Exception {
        when(bookingService.ViewTrainSectionDetails("sectionType", "journeyDate")).thenReturn(null);
        ResponseEntity<CustomResponse> response = bookingController.ViewTrainSectionDetails("sectionType", "journeyDate");
        Assertions.assertEquals("FAILURE", response.getBody().getMessage());
    }

    @Test
    public void testViewTrainSectionDetails6() throws Exception {
        when(bookingService.ViewTrainSectionDetails("sectionType", "journeyDate")).thenReturn(null);
        ResponseEntity<CustomResponse> response = bookingController.ViewTrainSectionDetails("sectionType", "journeyDate");
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testRemoveUserFromTrainSection() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertEquals("SUCCESS", response.getBody().getMessage());
    }

    @Test
    public void testRemoveUserFromTrainSection2() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testRemoveUserFromTrainSection3() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testRemoveUserFromTrainSection4() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertNotEquals(null, response.getBody());
    }

    @Test
    public void testRemoveUserFromTrainSection5() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertEquals("Removed the User from the Train Section Successfully", response.getBody().getOutput());
    }

    @Test
    public void testRemoveUserFromTrainSection6() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testRemoveUserFromTrainSection7() throws Exception {
        when(bookingService.removeUserFromTrainSection("email", "journeyDate")).thenReturn("SUCCESS");
        ResponseEntity<CustomResponse> response = bookingController.removeUserFromTrainSection("email", "journeyDate");
        Assertions.assertEquals("SUCCESS", response.getBody().getMessage());
    }

}
