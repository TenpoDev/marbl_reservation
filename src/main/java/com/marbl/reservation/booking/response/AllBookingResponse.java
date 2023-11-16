package com.marbl.reservation.booking.response;

import com.marbl.reservation.booking.Booking;
import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AllBookingResponse extends MarblResponse<List<Booking>> {

    @Serial
    private static final long serialVersionUID = 6918800934566730356L;
}
