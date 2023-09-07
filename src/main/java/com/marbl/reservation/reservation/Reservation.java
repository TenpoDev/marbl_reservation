package com.marbl.reservation.reservation;

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
@Table(uniqueConstraints = @UniqueConstraint( name = "RESERVED_BY", columnNames = "RESERVED_BY"))
public class Reservation implements Serializable {


    @Serial
    private static final long serialVersionUID = -2257895962332414038L;

    @Id
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
    private Long reservationId;
    @Column(name = "TITLE")
    private String reservationTitle;
    @Column(name = "NOTE")
    private String reservationNote;
    @Column(name = "PRIORITY")
    private ReservationPriority reservationPriority;
    @Column(name = "DATE")
    private LocalDate reservationDate;
    @Column(name = "HOUR_START")
    private LocalTime reservationStart;
    @Column(name = "HOUR_END")
    private LocalTime reservationEnd;
    @Column(name = "RESERVED_BY", nullable = false)
    private String reservationReservedById;

}
