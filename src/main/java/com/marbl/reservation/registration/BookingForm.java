package com.marbl.reservation.registration;

import com.marbl.reservation.registry.GenderOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingForm implements Serializable {
    @Serial
    private static final long serialVersionUID = 4788599427684285251L;

    private String userFirstName;
    private String userLastName;
    private String userAddress;
    private ContactForm contact;
    private LocalDate userBirth;
    private GenderOption userGender;
}
