package com.marbl.reservation.login;

import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registration.PasswordResetForm;
import com.marbl.reservation.shared.listener.RegistrationResendEventListener;
import com.marbl.reservation.token.verification.VerificationPassword;
import com.marbl.reservation.token.verification.VerificationPasswordRepository;
import com.marbl.reservation.user.User;
import com.marbl.reservation.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MarblService {

    private final PasswordEncoder passwordEncoder;

    private final RegistrationResendEventListener registrationResendEventListener;

    private final UserRepository userRepository;
    private final VerificationPasswordRepository verificationPasswordRepository;


    @Transactional
    public User resetPassword(PasswordResetForm passwordResetForm) throws MarblException {
        Optional<User> user = userRepository.findByRegistryContactEmail(passwordResetForm.getEmail());
//            registrationResendEventListener.onApplicationEvent(user.get().);
        if(user.isPresent() && user.get().isEnabled()) {

            //To-DO
            return null;
//            RegistrationResendEventListener
        }
        else if (passwordEncoder.matches(passwordResetForm.getOldPassword(), user.get().getPassword())) {
            //check logic: newPass compare  repeatNewPassword
            user.get().setPassword(passwordEncoder.encode(passwordResetForm.getNewPassword()));
            if (user.get().getVerificationPasswords().stream().anyMatch(psw ->
                    psw.getExpirationTime().compareTo(new Date()) < 0 && passwordEncoder.matches(passwordResetForm.getOldPassword(), psw.getPasswordHash())
            )) {
                throw new MarblException("Password was recently used.");
            }

            user.get().addVerificationPassword(new VerificationPassword(passwordEncoder.encode(passwordResetForm.getNewPassword())));

            return userRepository.save(user.get());
        } else {
            throw new MarblException("User not present, pls register first");
        }
    }
}
