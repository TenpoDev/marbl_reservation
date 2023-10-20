package com.marbl.reservation.user.response;

import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AllUserResponse extends MarblResponse<List<User>> {
    @Serial
    private static final long serialVersionUID = 2712807084650406258L;
}
