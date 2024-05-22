package com.train.london.france.booking.controller;

import com.sun.net.httpserver.Authenticator;
import com.train.london.france.booking.model.BookingDetailsDto;
import com.train.london.france.booking.model.CustomerDetailsDao;
import com.train.london.france.booking.model.SectionDetailsDto;
import com.train.london.france.booking.model.TicketDetailsDao;
import com.train.london.france.booking.model.CustomResponse;
import com.train.london.france.booking.service.interfaces.BookingService;
import com.train.london.france.booking.utils.BookingConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.train.london.france.booking.utils.BookingConstants.*;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private BookingService bookingService;
    /**
     * Method for booking a train ticket from London to France.
     *
     * @param ticketDetailsDao
     * @return customResponse
     */
    @Tag(name = "Train Booking APIs")
    @Operation(summary="Book a train ticket from London to France")
    @PostMapping("/BookTicket")
    public ResponseEntity<CustomResponse> bookTicket(
            @RequestBody TicketDetailsDao ticketDetailsDao) {
        LOGGER.info(messageSource.getMessage("info.bookingTicketDetails.book", null, Locale.ENGLISH));
        CustomResponse customResponse;
        try {
            BookingDetailsDto bookedTicketDetails = bookingService.bookTicket(ticketDetailsDao);
            if (bookedTicketDetails != null) {
                customResponse = CustomResponse.builder().message(SUCCESS)
                        .statusCode(BookingConstants.SUCCESS_STATUS_CODE).output(bookedTicketDetails).build();
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                customResponse = CustomResponse.builder().message(FAILURE)
                        .statusCode(FAILURE_NOT_FOUND_STATUS_CODE).output(null).build();
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in BookTicket: ", e);
            customResponse = CustomResponse.builder().message(FAILURE)
                    .statusCode(BookingConstants.FAILURE_BAD_REQUEST_STATUS_CODE).output(null).build();
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method for booking a train ticket from London to France.
     *
     * @param sectionType
     * @param journeyDate
     * @return customResponse
     */
    @Tag(name = "Train Booking APIs")
    @Operation(summary="View the users and seats of a train section")
    @PostMapping("/ViewTrainSectionDetails")
    public ResponseEntity<CustomResponse> ViewTrainSectionDetails(
            @RequestParam String sectionType, @RequestParam String journeyDate) {
        LOGGER.info(messageSource.getMessage("info.ViewTrainSectionDetails.view", null, Locale.ENGLISH));
        CustomResponse customResponse;
        try {
            List<SectionDetailsDto> sectionDetailsDto = bookingService.ViewTrainSectionDetails(sectionType, journeyDate);
            if (sectionDetailsDto != null) {
                customResponse = CustomResponse.builder().message(SUCCESS)
                        .statusCode(BookingConstants.SUCCESS_STATUS_CODE).output(sectionDetailsDto).build();
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                customResponse = CustomResponse.builder().message(FAILURE)
                        .statusCode(FAILURE_NOT_FOUND_STATUS_CODE).output(null).build();
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in BookTicket: ", e);
            customResponse = CustomResponse.builder().message(FAILURE)
                    .statusCode(BookingConstants.FAILURE_BAD_REQUEST_STATUS_CODE).output(null).build();
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name = "Train Booking APIs")
    @Operation(summary="Remove the user from the Train Section")
    @PostMapping("/removeUserFromTrainSection")
    public ResponseEntity<CustomResponse> removeUserFromTrainSection(
            @RequestBody   BookingDetailsDto bookingDetailsDto ) {
        LOGGER.info(messageSource.getMessage("info.removeUserFromTrainSection.remove", null, Locale.ENGLISH));
        CustomResponse customResponse;
        try {
            String message = bookingService.removeUserFromTrainSection(bookingDetailsDto);
            if (message.equalsIgnoreCase(SUCCESS)) {
                customResponse = CustomResponse.builder().message(SUCCESS)
                        .statusCode(SUCCESS_STATUS_CODE).output("Removed the User from the Train Section Successfully").build();
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                customResponse = CustomResponse.builder().message(FAILURE)
                        .statusCode(FAILURE_NOT_FOUND_STATUS_CODE).output(message).build();
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in BookTicket: ", e);
            customResponse = CustomResponse.builder().message(FAILURE)
                    .statusCode(FAILURE_BAD_REQUEST_STATUS_CODE).output(null).build();
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name = "Train Booking APIs")
    @Operation(summary="Modify the ticket of the user")
    @PostMapping("/modifyUserTicket")
    public ResponseEntity<CustomResponse> modifyUserTicket(
            @RequestBody CustomerDetailsDao customerDetailsDao, @RequestParam String journeyDate, @RequestParam int seatNumber, @RequestParam String sectionType) {
        LOGGER.info(messageSource.getMessage("info.modifyUserTicket.modify", null, Locale.ENGLISH));
        CustomResponse customResponse;
        try {
            BookingDetailsDto bookingDetailsDto = bookingService.modifyUserTicket(customerDetailsDao, journeyDate, seatNumber,sectionType);
            if (bookingDetailsDto != null) {
                customResponse = CustomResponse.builder().message(SUCCESS)
                        .statusCode(SUCCESS_STATUS_CODE).output(bookingDetailsDto).build();
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                customResponse = CustomResponse.builder().message(FAILURE)
                        .statusCode(FAILURE_NOT_FOUND_STATUS_CODE).output("No Tickets Available").build();
                return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in BookTicket: ", e);
            customResponse = CustomResponse.builder().message(FAILURE)
                    .statusCode(FAILURE_BAD_REQUEST_STATUS_CODE).output(null).build();
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
    }


}
