package com.marbl.reservation.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ReservationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -2258974715440826340L;

    @JsonProperty("timestamp")
    private OffsetDateTime timestamp;
    @JsonProperty("path")
    private String path;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("exceptionCode")
    private String exceptionCode;
    @JsonProperty("exceptionMessage")
    private String exceptionMessage;

    @JsonProperty("result")
    private List<Reservation> result;

}
