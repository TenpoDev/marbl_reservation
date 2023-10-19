package com.marbl.reservation.reservation.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 7985473233497774078L;
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
    private List<User> result;
}
