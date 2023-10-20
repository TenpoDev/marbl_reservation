package com.marbl.reservation.event.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marbl.reservation.event.Reservation;
import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleReservationResponse extends MarblResponse<Reservation> {
    @Serial
    private static final long serialVersionUID = 6711740171264483134L;
}
