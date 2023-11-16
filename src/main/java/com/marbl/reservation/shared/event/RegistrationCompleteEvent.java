package com.marbl.reservation.shared.event;

import com.marbl.reservation.user.User;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -4614591558670403855L;

    private User user;
    private String oldToken;
    private String applicationUrl;
    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
