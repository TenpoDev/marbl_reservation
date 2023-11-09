package com.marbl.reservation.shared.listener;

import com.marbl.reservation.registration.RegistrationService;
import com.marbl.reservation.shared.event.RegistrationCompleteEvent;
import com.marbl.reservation.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private static final String VERIFY_REGISTRATION = "/api/v1/registration/verify?token=";
    private final RegistrationService registrationService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the USER with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        registrationService.saveVerificationTokenForUser(token, user);
        registrationService.saveVerificationPasswordForUser(user);
        //Send Mail To User
        String url = event.getApplicationUrl().concat(VERIFY_REGISTRATION).concat(token);

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}", url
        );
    }
}
