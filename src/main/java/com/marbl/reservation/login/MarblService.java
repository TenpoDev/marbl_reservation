package com.marbl.reservation.login;

import com.marbl.reservation.config.JwtService;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registration.PasswordResetForm;
import com.marbl.reservation.shared.event.RegistrationResendEvent;
import com.marbl.reservation.token.verification.VerificationPassword;
import com.marbl.reservation.user.User;
import com.marbl.reservation.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MarblService {

    private final JwtService jwtService;

    private final HttpServletRequest httpServletRequest;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public String login(LoginRequest loginRequest) throws MarblException {
        log.info("Attempting to authenticate user: {}", loginRequest.getUsername());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            log.info("Authentication successful for user: {}", loginRequest.getUsername());
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", loginRequest.getUsername(), e);
            throw new MarblException("User or Password are wrong");
        }

        var user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new MarblException("User or Password are wrong");
        }

        return jwtService.generateToken(user.get());
    }


    @Transactional
    public User changePassword(PasswordResetForm passwordResetForm) throws MarblException {
        Optional<User> user = userRepository.findByUsername(passwordResetForm.getUsername());
        if (user.isEmpty()) {
            throw new MarblException("User is not present, pls go to registration"); //to-change with better message
        } else if (!user.get().isEnabled()) {

            applicationEventPublisher.publishEvent(new RegistrationResendEvent("", applicationUrl(httpServletRequest)));
            return null;
        } else if (passwordEncoder.matches(passwordResetForm.getOldPassword(), user.get().getPassword())) {
            //check logic: newPass compare repeatNewPassword | Create utils class
            user.get().setPassword(passwordEncoder.encode(passwordResetForm.getNewPassword()));
            if (user.get().getVerificationPasswords().stream().anyMatch(psw -> psw.getExpirationTime().compareTo(new Date()) < 0 && passwordEncoder.matches(passwordResetForm.getOldPassword(), psw.getPasswordHash()))) {
                throw new MarblException("Password was recently used."); //to-change with better message
            }
            user.get().addVerificationPassword(new VerificationPassword(passwordEncoder.encode(passwordResetForm.getNewPassword())));

            return userRepository.save(user.get());
        } else {
            throw new MarblException("Password is wrong, pls insert the old password"); //to-change with better message
        }
    }

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
    }


}
