package com.marbl.reservation.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -4737574917753126786L;

    private String email;

    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
}
