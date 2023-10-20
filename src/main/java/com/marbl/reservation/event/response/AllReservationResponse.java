package com.marbl.reservation.event.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marbl.reservation.event.Reservation;
import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AllReservationResponse extends MarblResponse<List<Reservation>> {

    @Serial
    private static final long serialVersionUID = 6918800934566730356L;
}
