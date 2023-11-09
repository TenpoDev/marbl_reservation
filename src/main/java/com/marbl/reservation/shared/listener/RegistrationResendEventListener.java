package com.marbl.reservation.shared.listener;

import com.marbl.reservation.registration.RegistrationService;
import com.marbl.reservation.shared.event.RegistrationCompleteEvent;
import com.marbl.reservation.shared.event.RegistrationResendEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RegistrationResendEventListener implements ApplicationListener<RegistrationResendEvent> {
    private static final String VERIFY_REGISTRATION = "/api/v1/registration/verify?token=";
    private final RegistrationService registrationService;

    @Override
    public void onApplicationEvent(RegistrationResendEvent event) {

        String token = registrationService.generateNewVerifyToken(event.getOldToken());

        String url = event.getApplicationUrl().concat(VERIFY_REGISTRATION).concat(token);

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}", url
        );
    }
}
