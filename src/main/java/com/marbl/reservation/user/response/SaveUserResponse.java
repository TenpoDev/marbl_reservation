package com.marbl.reservation.user.response;

import com.marbl.reservation.shared.MarblResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaveUserResponse extends MarblResponse<Long> {
    @Serial
    private static final long serialVersionUID = 3112059785429906027L;
}
