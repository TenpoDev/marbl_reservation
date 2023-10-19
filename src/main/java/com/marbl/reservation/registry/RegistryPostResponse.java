package com.marbl.reservation.registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class RegistryPostResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 740621540940508087L;

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
    private Long registryId;
}
