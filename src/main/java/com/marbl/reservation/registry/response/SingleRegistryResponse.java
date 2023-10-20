package com.marbl.reservation.registry.response;

import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tomcat.util.modeler.Registry;

import java.io.Serial;
@Data
@EqualsAndHashCode(callSuper = true)
public class SingleRegistryResponse extends MarblResponse<Registry> {
    @Serial
    private static final long serialVersionUID = -6648754142293217646L;
}
