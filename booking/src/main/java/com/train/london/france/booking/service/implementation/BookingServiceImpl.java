package com.train.london.france.booking.service.implementation;

import com.train.london.france.booking.enums.BookingStatus;
import com.train.london.france.booking.enums.SeatTypes;
import com.train.london.france.booking.model.BookingDetailsDao;
import com.train.london.france.booking.model.BookingDetailsDto;
import com.train.london.france.booking.model.CustomerDetailsDao;
import com.train.london.france.booking.model.PaymentDetailsDao;
import com.train.london.france.booking.model.SectionDetailsDto;
import com.train.london.france.booking.model.TicketDetailsDao;
import com.train.london.france.booking.repository.BookingDetailsRepository;
import com.train.london.france.booking.repository.CustomerDetailsRepository;
import com.train.london.france.booking.repository.PaymentDetailsRepository;
import com.train.london.france.booking.repository.TicketDetailsRepository;
import com.train.london.france.booking.service.interfaces.BookingService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static com.train.london.france.booking.utils.BookingConstants.*;

@Service
public class BookingServiceImpl implements BookingService {

    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BookingServiceImpl.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;
    @Autowired
    private TicketDetailsRepository ticketDetailsRepository;
    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;
    private static Map<SeatTypes, List<Integer>> seatMap = Map.of
            (SeatTypes.Window, Arrays.asList(1, 4, 7, 10),
                    SeatTypes.Middle, Arrays.asList(2, 5, 8, 11),
                    SeatTypes.Aisle, Arrays.asList(3, 6, 9, 12));

    private static Map<String, Map> trainSectionMap = new HashMap<>() {{
        put("Section-A", seatMap);
        put("Section-B", seatMap);
    }};

    private static Map<String, Map<String, List<Integer>>> bookedTickets = new HashMap<>();


    @Override
    public BookingDetailsDto bookTicket(TicketDetailsDao ticketDetailsDao) {
        LOGGER.info(messageSource.getMessage("method.bookTicket", null, Locale.ENGLISH));
        try {
            String sectionType = ticketDetailsDao.getCustomerDetailsDao().getSectionPreference();
            List<Integer> bookedSeats = ticketDetailsRepository.viewTrainSectionDetails(sectionType,ticketDetailsDao.getBookingDetailsDao().getJourneyDate())
                    .stream().collect(ArrayList::new, (list, ticket) -> list.add(ticket.getSeatNumber()), ArrayList::addAll);
            final SeatTypes[] seatType = new SeatTypes[1];
            if (bookedSeats.size() == 12) {
                sectionType = ticketDetailsDao.getCustomerDetailsDao().getSectionPreference().equals(SECTIONA) ? SECTIONB : SECTIONA;
                bookedSeats = ticketDetailsRepository.viewTrainSectionDetails(sectionType,ticketDetailsDao.getBookingDetailsDao().getJourneyDate())
                        .stream().collect(ArrayList::new, (list, ticket) -> list.add(ticket.getSeatNumber()), ArrayList::addAll);
                if (bookedSeats.size() == 12) {
                    ticketDetailsDao.setSeatNumber(0);
                    ticketDetailsDao.setBookingStatus(BookingStatus.WAITING);
                    seatType[0] = SeatTypes.NA;
                    ticketDetailsDao.setSectionType("NA");
                }
            }
            if (null==ticketDetailsDao.getBookingStatus()) {
                List<Integer> availableSeats = IntStream.rangeClosed(1, 12)
                        .boxed().toList();
                Random rand = new Random();
                Integer seatNumber = availableSeats.get(rand.nextInt(availableSeats.size()));
                while (bookedSeats.contains(seatNumber)) {
                    seatNumber = availableSeats.get(rand.nextInt(availableSeats.size()));
                }
                ticketDetailsDao.setSeatNumber(seatNumber);
                ticketDetailsDao.setBookingStatus(BookingStatus.CONFIRMED);
                ticketDetailsDao.setSectionType(sectionType);
                //To get the SeatType
                seatMap.keySet().forEach(key -> {
                    if (seatMap.get(key).contains(ticketDetailsDao.getSeatNumber())) {
                        seatType[0] = key;
                    }
                });
            }
            //Can be used for in-memory storage of booked seats
                /* Map<String, List<Integer>> bookedSeatMap = new HashMap<>();
                bookedSeatMap.put("Section-A", Arrays.asList(ticketDetailsDao.getSeatNumber()));
                bookedTickets.put(ticketDetailsDao.getBookingDetailsDao().getJourneyDate(), bookedSeatMap);*/

            //To save the booking details
            BookingDetailsDao bookingDetailsDao = BookingDetailsDao.builder()
                    .bookingDate(ticketDetailsDao.getBookingDetailsDao().getBookingDate())
                    .journeyDate(ticketDetailsDao.getBookingDetailsDao().getJourneyDate())
                    .journeyTime(ticketDetailsDao.getBookingDetailsDao().getJourneyTime())
                    .startPlace(ticketDetailsDao.getBookingDetailsDao().getStartPlace())
                    .endPlace(ticketDetailsDao.getBookingDetailsDao().getEndPlace())
                    .build();
            bookingDetailsRepository.save(bookingDetailsDao);

            //To save the customer details
            CustomerDetailsDao customerDetailsDao = CustomerDetailsDao.builder()
                    .firstName(ticketDetailsDao.getCustomerDetailsDao().getFirstName())
                    .lastName(ticketDetailsDao.getCustomerDetailsDao().getLastName())
                    .dateOfBirth(ticketDetailsDao.getCustomerDetailsDao().getDateOfBirth())
                    .email(ticketDetailsDao.getCustomerDetailsDao().getEmail())
                    .phoneNumber(ticketDetailsDao.getCustomerDetailsDao().getPhoneNumber())
                    .city(ticketDetailsDao.getCustomerDetailsDao().getCity())
                    .country(ticketDetailsDao.getCustomerDetailsDao().getCountry())
                    .passportNumber(ticketDetailsDao.getCustomerDetailsDao().getPassportNumber())
                    .seatPreference(ticketDetailsDao.getCustomerDetailsDao().getSeatPreference())
                    .sectionPreference(ticketDetailsDao.getCustomerDetailsDao().getSectionPreference())
                    .build();
            customerDetailsRepository.save(customerDetailsDao);

            //To save the payment details
            PaymentDetailsDao paymentDetailsDao = PaymentDetailsDao.builder()
                    .amount(ticketDetailsDao.getPaymentDetailsDao().getAmount())
                    .paymentType(ticketDetailsDao.getPaymentDetailsDao().getPaymentType())
                    .build();
            paymentDetailsRepository.save(paymentDetailsDao);

            //To save the ticket details
            TicketDetailsDao bookedTicketDetails = TicketDetailsDao.builder()
                    .bookingDetailsDao(bookingDetailsDao)
                    .customerDetailsDao(customerDetailsDao)
                    .bookingStatus(ticketDetailsDao.getBookingStatus())
                    .seatNumber(ticketDetailsDao.getSeatNumber())
                    .seatType(seatType[0])
                    .sectionType(ticketDetailsDao.getSectionType())
                    .paymentDetailsDao(paymentDetailsDao)
                    .build();
            ticketDetailsRepository.save(bookedTicketDetails);
            LOGGER.info(messageSource.getMessage("success.bookTicket", null, Locale.ENGLISH));
            //return the booking details reciept
            return BookingDetailsDto.builder()
                    .bookingId(bookingDetailsDao.getBookingId().toString())
                    .bookingDate(bookingDetailsDao.getBookingDate())
                    .journeyDate(bookingDetailsDao.getJourneyDate())
                    .journeyTime(bookingDetailsDao.getJourneyTime())
                    .startPlace(bookingDetailsDao.getStartPlace())
                    .endPlace(bookingDetailsDao.getEndPlace())
                    .bookingStatus(bookedTicketDetails.getBookingStatus())
                    .firstName(customerDetailsDao.getFirstName())
                    .lastName(customerDetailsDao.getLastName())
                    .email(customerDetailsDao.getEmail())
                    .pricePaid(paymentDetailsDao.getAmount())
                    .seatNumber(bookedTicketDetails.getSeatNumber())
                    .seatType(bookedTicketDetails.getSeatType())
                    .sectionType(bookedTicketDetails.getSectionType())
                    .build();
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("error.bookTicket", null, Locale.ENGLISH));
            return null;
        }
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int[] exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }


    @Override
    public List<SectionDetailsDto> ViewTrainSectionDetails(String sectionType, String journeyDate) {
        try {
            LOGGER.info(messageSource.getMessage("method.ViewTrainSectionDetails", null, Locale.ENGLISH));
            List<TicketDetailsDao> ticketDetailsDao = ticketDetailsRepository.viewTrainSectionDetails(sectionType, journeyDate);
            if (ticketDetailsDao.isEmpty()) {
                LOGGER.error(messageSource.getMessage("error.ViewTrainSectionDetails", null, Locale.ENGLISH));
                return null;
            } else {
                List<SectionDetailsDto> sectionDetailsDtoList = new ArrayList<>();
                ticketDetailsDao.forEach(ticket -> {
                    SectionDetailsDto sectionDetailsDto = SectionDetailsDto.builder()
                            .sectionType(sectionType)
                            .journeyDate(journeyDate)
                            .customerDetailsDao(ticket.getCustomerDetailsDao())
                            .seatNumber(ticket.getSeatNumber())
                            .seatType(ticket.getSeatType())
                            .build();
                    sectionDetailsDtoList.add(sectionDetailsDto);
                });
                return sectionDetailsDtoList;
            }
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("error.ViewTrainSectionDetails", null, Locale.ENGLISH));
            return null;
        }
    }


    @Override
    public String removeUserFromTrainSection(BookingDetailsDto bookingDetailsDto) {
        try {
            LOGGER.info(messageSource.getMessage("method.removeUserFromTrainSection", null, Locale.ENGLISH));
            TicketDetailsDao ticketDetailsDao = ticketDetailsRepository.findByEmailAndJourneyDate(bookingDetailsDto.getEmail(), bookingDetailsDto.getJourneyDate(), bookingDetailsDto.getSeatNumber(), bookingDetailsDto.getSectionType());
            int a = ticketDetailsRepository.removeUserFromTrainSection(ticketDetailsDao.getTicketId());
            if (a >= 1) {
                return SUCCESS;
            } else {
                return FAILURE;
            }
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("error.removeUserFromTrainSection", null, Locale.ENGLISH));
            return FAILURE;
        }
    }


    @Override
    public BookingDetailsDto modifyUserTicket(CustomerDetailsDao customerDetailsDao, String journeyDate,int seatNumber) {
        try {
            LOGGER.info(messageSource.getMessage("method.modifyUserTicket", null, Locale.ENGLISH));
            String sectionType = customerDetailsDao.getSectionPreference();
            TicketDetailsDao ticketDetailsDao = ticketDetailsRepository.findByEmailAndJourneyDate(customerDetailsDao.getEmail(), journeyDate,seatNumber,sectionType);
            List<Integer> bookedSeats = ticketDetailsRepository.viewBookedSeats(sectionType, journeyDate, customerDetailsDao.getSeatPreference().toString())
                    .stream().collect(ArrayList::new, (list, ticket) -> list.add(ticket.getSeatNumber()), ArrayList::addAll);
            if (bookedSeats.size() == 4) {
                sectionType = customerDetailsDao.getSectionPreference().equals(SECTIONA) ? SECTIONB : SECTIONA;
                bookedSeats = ticketDetailsRepository.viewBookedSeats(sectionType, journeyDate, customerDetailsDao.getSeatPreference().toString())
                        .stream().collect(ArrayList::new, (list, ticket) -> list.add(ticket.getSeatNumber()), ArrayList::addAll);
                if (bookedSeats.size() == 4) {
                    LOGGER.error(messageSource.getMessage("error.seatAlreadyBooked", null, Locale.ENGLISH));
                    return new BookingDetailsDto();
                }
            }
                Random rand = new Random();
                    seatNumber = seatMap.get(customerDetailsDao.getSeatPreference().toString()).get(rand.nextInt(4));
                while (bookedSeats.contains(seatNumber)) {
                    seatNumber = seatMap.get(customerDetailsDao.getSeatPreference().toString()).get(rand.nextInt(4));
                }
                ticketDetailsDao.setSeatNumber(seatNumber);
                ticketDetailsDao.setSeatType(SeatTypes.valueOf(customerDetailsDao.getSeatPreference().toString()));
                ticketDetailsDao.setSectionType(customerDetailsDao.getSectionPreference());
                ticketDetailsRepository.save(ticketDetailsDao);
                return BookingDetailsDto.builder()
                        .bookingId(ticketDetailsDao.getBookingDetailsDao().getBookingId().toString())
                        .bookingDate(ticketDetailsDao.getBookingDetailsDao().getBookingDate())
                        .journeyDate(ticketDetailsDao.getBookingDetailsDao().getJourneyDate())
                        .journeyTime(ticketDetailsDao.getBookingDetailsDao().getJourneyTime())
                        .startPlace(ticketDetailsDao.getBookingDetailsDao().getStartPlace())
                        .endPlace(ticketDetailsDao.getBookingDetailsDao().getEndPlace())
                        .bookingStatus(ticketDetailsDao.getBookingStatus())
                        .firstName(ticketDetailsDao.getCustomerDetailsDao().getFirstName())
                        .lastName(ticketDetailsDao.getCustomerDetailsDao().getLastName())
                        .email(ticketDetailsDao.getCustomerDetailsDao().getEmail())
                        .pricePaid(ticketDetailsDao.getPaymentDetailsDao().getAmount())
                        .seatNumber(ticketDetailsDao.getSeatNumber())
                        .seatType(ticketDetailsDao.getSeatType())
                        .sectionType(ticketDetailsDao.getSectionType())
                        .build();
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("error.modifyUserTicket", null, Locale.ENGLISH));
            return null;
        }
    }
}
