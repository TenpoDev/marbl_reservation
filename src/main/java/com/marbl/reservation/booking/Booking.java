package com.marbl.reservation.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {


    @Serial
    private static final long serialVersionUID = -2257895962332414038L;

    @Id
    @SequenceGenerator(name = "booking_sequence", sequenceName = "booking_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_sequence")
    private Long bookingId;
    @Column(name = "TITLE")
    private String bookingTitle;
    @Column(name = "DESCRIPTION")
    private String bookingDescription;
    @Column(name = "LOCATION")
    private String bookingLocation;
    @Column(name = "PRIORITY")
    private BookingPriority bookingPriority;
    @Column(name = "DATE")
    private LocalDate bookingDate;
    @Column(name = "HOUR_START")
    private LocalTime bookingStart;
    @Column(name = "HOUR_END")
    private LocalTime bookingEnd;

}
