package com.marbl.reservation.shared.listener;

import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registration.RegistrationService;
import com.marbl.reservation.shared.event.RegistrationCompleteEvent;
import com.marbl.reservation.shared.event.RegistrationResendEvent;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public void onApplicationEvent(RegistrationResendEvent event) {
        log.info("Handling resend token event...");

        try {
            String newToken = registrationService.generateNewVerifyToken(event.getOldToken());
            String verificationUrl = event.getApplicationUrl().concat(VERIFY_REGISTRATION).concat(newToken);

            sendVerificationEmail(event.getOldToken(), verificationUrl);

            log.info("Verification link sent successfully: {}", verificationUrl);
        } catch (MarblException e) {
            log.error("Error resending registration verification: {}", e.getMessage());
            throw new MarblException(e.getMessage());
        }
    }

    private void sendVerificationEmail(String email, String verificationUrl) {
        log.info("Sending verification email to {}: {}", email, verificationUrl);
        // Implementa il codice per l'invio effettivo dell'email di verifica
    }

}
