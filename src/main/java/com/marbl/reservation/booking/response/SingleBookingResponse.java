package com.marbl.reservation.booking.response;

import com.marbl.reservation.booking.Booking;
import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleBookingResponse extends MarblResponse<Booking> {
    @Serial
    private static final long serialVersionUID = 6711740171264483134L;
}
