package com.marbl.reservation.shared.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

@Getter
@Setter
public class RegistrationResendEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -4614591558670403855L;

    private String oldToken;
    private String applicationUrl;

    public RegistrationResendEvent(String token, String applicationUrl) {
        super(token);
        this.oldToken = token;
        this.applicationUrl = applicationUrl;
    }
}
