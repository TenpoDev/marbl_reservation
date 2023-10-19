package com.marbl.reservation.reservation.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class UserPostResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 5039710100084066671L;
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
    private Long userId;
}
