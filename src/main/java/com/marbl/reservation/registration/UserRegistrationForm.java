package com.marbl.reservation.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -3025089423749682883L;

    private String userName;
    private String password;
    private String matchingPassword;
    private BookingForm booking;

}
