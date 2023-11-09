package com.marbl.reservation.shared.listener;

import com.marbl.reservation.registration.RegistrationService;
import com.marbl.reservation.shared.event.RegistrationResetPasswordEvent;
import com.marbl.reservation.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class RegistrationResetPasswordEventListener implements ApplicationListener<RegistrationResetPasswordEvent> {

    private static final String SAVE_REGISTRATION = "/public/v1/passwords?password=";
    private static final String REGISTRATION = "/api/v1/registration";

    private final RegistrationService registrationService;

    @Override
    public void onApplicationEvent(RegistrationResetPasswordEvent event) {
        User user = registrationService.resetPassword(event.getEmail());

        if(Objects.nonNull(user)) {
            String url = event.getApplicationUrl().concat(SAVE_REGISTRATION).concat( user.getPassword());
            log.info("Click the link to login and update your password: {}", url
            );
        } else {
            String url = event.getApplicationUrl().concat(REGISTRATION);
            log.info("Click the link to registration: {}", url
            );
        }


    }
}
