package com.marbl.reservation.user.response;

import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleUserResponse extends MarblResponse<User> {
    @Serial
    private static final long serialVersionUID = -317391244881264227L;
}
