package com.marbl.reservation.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class ReservationPostResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -886284560706112077L;

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

    @JsonProperty("reservationId")
    private Long reservationId;
}
