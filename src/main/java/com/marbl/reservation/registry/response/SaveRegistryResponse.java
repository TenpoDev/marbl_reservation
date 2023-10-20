package com.marbl.reservation.registry.response;

import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
@Data
@EqualsAndHashCode(callSuper = true)
public class SaveRegistryResponse extends MarblResponse<Long> {
    @Serial
    private static final long serialVersionUID = -3724643518046583392L;
}
