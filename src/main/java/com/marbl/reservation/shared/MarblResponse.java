package com.marbl.reservation.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MarblResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1350009163935783972L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T data;

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

    public MarblResponse(OffsetDateTime timestamp, String path, Integer status) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
    }

    public MarblResponse(OffsetDateTime timestamp, String path, Integer status, T data) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.data= data;
    }
}


