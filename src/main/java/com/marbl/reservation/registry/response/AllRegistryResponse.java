package com.marbl.reservation.registry.response;

import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tomcat.util.modeler.Registry;

import java.io.Serial;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = true)
public class AllRegistryResponse extends MarblResponse<List<Registry>> {
    @Serial
    private static final long serialVersionUID = -5309405820431693059L;
}
