package com.marbl.reservation.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaveResponse extends MarblResponse<Long> {

    @Serial
    private static final long serialVersionUID = 4634322137383679703L;
}
