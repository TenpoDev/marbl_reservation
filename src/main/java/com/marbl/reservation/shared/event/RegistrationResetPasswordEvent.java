package com.marbl.reservation.shared.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

@Getter
@Setter
public class RegistrationResetPasswordEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = 2455570449927331101L;

    private String email;
    private String applicationUrl;

    public RegistrationResetPasswordEvent(String email, String applicationUrl) {
        super(email);
        this.email = email;
        this.applicationUrl = applicationUrl;
    }
}
