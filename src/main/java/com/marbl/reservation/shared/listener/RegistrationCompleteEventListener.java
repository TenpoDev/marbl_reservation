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
        log.info("Handling registration completion event...");

        User user = event.getUser();

        // Create the Verification Token for the USER with link
        String verificationToken = UUID.randomUUID().toString();
        registrationService.saveVerificationTokenForUser(verificationToken, user);
        registrationService.saveVerificationPasswordForUser(user);

        // Send Mail To User
        String verificationUrl = event.getApplicationUrl().concat(VERIFY_REGISTRATION).concat(verificationToken);
        sendVerificationEmail(user.getUsername(), verificationUrl);

        log.info("Verification link sent successfully: {}", verificationUrl);
    }

    private void sendVerificationEmail(String email, String verificationUrl) {
        log.info("Sending verification email to {}: {}", email, verificationUrl);
        // Implementa il codice per l'invio effettivo dell'email di verifica
    }

}
