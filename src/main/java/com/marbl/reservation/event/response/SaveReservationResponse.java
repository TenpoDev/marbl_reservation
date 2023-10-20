package com.marbl.reservation.event.response;

import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaveReservationResponse extends MarblResponse<Long> {

    @Serial
    private static final long serialVersionUID = -886284560706112077L;

}
