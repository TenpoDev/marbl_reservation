package com.marbl.reservation.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Serializable {


    @Serial
    private static final long serialVersionUID = -2257895962332414038L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Missing Reservation Name")
    @NotNull(message = "Missing Reservation Name")
    private String name;
    private String reservedBy;
}
