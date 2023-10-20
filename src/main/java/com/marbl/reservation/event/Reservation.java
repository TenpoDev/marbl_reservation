package com.marbl.reservation.event;

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
public class Reservation implements Serializable {


    @Serial
    private static final long serialVersionUID = -2257895962332414038L;

    @Id
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
    private Long reservationId;
    @Column(name = "TITLE")
    private String reservationTitle;
    @Column(name = "DESCRIPTION")
    private String reservationDescription;
    @Column(name = "LOCATION")
    private String reservationLocation;
    @Column(name = "PRIORITY")
    private ReservationPriority reservationPriority;
    @Column(name = "DATE")
    private LocalDate reservationDate;
    @Column(name = "HOUR_START")
    private LocalTime reservationStart;
    @Column(name = "HOUR_END")
    private LocalTime reservationEnd;

}
