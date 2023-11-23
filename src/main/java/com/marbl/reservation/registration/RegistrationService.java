package com.marbl.reservation.registration;

import com.marbl.reservation.config.JwtService;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registry.Contact;
import com.marbl.reservation.registry.Registry;
import com.marbl.reservation.token.verification.VerificationPassword;
import com.marbl.reservation.token.verification.VerificationPasswordRepository;
import com.marbl.reservation.token.verification.VerificationToken;
import com.marbl.reservation.token.verification.VerificationTokenRepository;
import com.marbl.reservation.user.Role;
import com.marbl.reservation.user.User;
import com.marbl.reservation.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationPasswordRepository verificationPasswordRepository;

    private final JwtService jwtService;

    @Transactional
    public User saveNewUser(UserRegistrationForm userRequest) {
        log.info("Attempting to register for user: {}", userRequest.getUsername());

        //controllare che le pass sono uguali
        Contact contactForm = Contact.builder()
                .mobile(userRequest.getBooking().getContact().getMobile())
                .build();

        Registry registryForm = Registry.builder()
                .userFirstName(userRequest.getBooking().getUserFirstName())
                .userLastName(userRequest.getBooking().getUserLastName())
                .userAddress(userRequest.getBooking().getUserAddress())
                .userGender(userRequest.getBooking().getUserGender())
                .userBirth(userRequest.getBooking().getUserBirth())
                .contact(contactForm)
                .build();

        User user = User.builder().username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .registry(registryForm)
                .role(Role.USER)
                .enabled(Boolean.FALSE)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User saved successfully.");
        log.info("Token::" + jwtService.generateToken(user));

        return savedUser;

    }

    @Transactional
    public void saveVerificationTokenForUser(String token, User user) {
        log.info("Attempting to save Verification Token for user: {}", user);
        VerificationToken verificationToken = new VerificationToken(token, user);

        verificationTokenRepository.save(verificationToken);
        log.info("Verification Token saved successfully for user: {}", user);
    }

    @Transactional
    public void saveVerificationPasswordForUser(User user) {
        log.info("Attempting to save Verification Password for user: {}", user);
        VerificationPassword verificationPassword = new VerificationPassword(user.getPassword(), user);

        verificationPasswordRepository.save(verificationPassword);
        log.info("Verification Password saved successfully for user: {}", user);
    }

    @Transactional
    public void verifyToken(String token) throws MarblException {
        log.info("Attempting to verify token: {}", token);

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (Objects.isNull(verificationToken)) {
            log.error("Verification Token verification failed. Token not found.");
            throw new MarblException("User not registered.");
        }
        //Change calendar
        Calendar cal = Calendar.getInstance();
        if (verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            log.error("Verification Token verification failed. Verification Token expired for user: {}", verificationToken.getUser().getUsername());
            throw new MarblException("Verification Token is expired.");
        }

        User user = verificationToken.getUser();
        user.setEnabled(Boolean.TRUE);
        userRepository.save(user);

        log.info("Verification Token verified successfully for user: {}", user.getUsername());
    }

    @Transactional
    public String generateNewVerifyToken(String oldToken) throws MarblException {
        log.info("Attempting to verify old Verification Token: {}", oldToken);

        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);

        if (verificationToken == null) {
            log.error("Old Verification Token not found: {}", oldToken);
            throw new MarblException("Old token not found");
        }

        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        verificationTokenRepository.save(verificationToken);

        log.info("New Verification Token stored successfully.");

        return newToken;
    }


    @Transactional
    public User resetPassword(String username) throws MarblException {
        log.info("Resetting password for user with username: {}", username);
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            String newPassword = RandomStringUtils.randomNumeric(8);
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            User savedUser = userRepository.save(user);
            log.info("Password reset successful for user: {}", username);

            return savedUser;
        } else {
            log.error("User not found for username: {}", username);
            throw new MarblException("User not found for username: " + username);
        }
    }

}
