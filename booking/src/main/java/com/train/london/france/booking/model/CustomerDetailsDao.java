package com.train.london.france.booking.model;

import com.train.london.france.booking.enums.SeatTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CustomerDetailsDao {

    @Id
    @GeneratedValue
    private UUID customerId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    //@Column(unique = true)
    private String email;
    private String phoneNumber;
    private String city;
    private String country;
    private String passportNumber;
    @Enumerated(EnumType.STRING)
    private SeatTypes seatPreference;
    private String sectionPreference;

}
