package com.marbl.reservation.registration;

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

    @Transactional
    public User saveNewUser(UserRegistrationForm userRequest) {
        //controllare che le pass sono uguali
        Contact contactForm = Contact.builder().email(userRequest.getBooking().getContact().getEmail()).mobile(userRequest.getBooking().getContact().getMobile()).build();
        Registry registryForm = Registry.builder().userFirstName(userRequest.getBooking().getUserFirstName()).userLastName(userRequest.getBooking().getUserLastName()).userAddress(userRequest.getBooking().getUserAddress()).userGender(userRequest.getBooking().getUserGender()).userBirth(userRequest.getBooking().getUserBirth()).contact(contactForm).build();
        User user = User.builder().userName(userRequest.getUserName()).password(passwordEncoder.encode(userRequest.getPassword())).registry(registryForm).role(Role.USER).enabled(Boolean.FALSE).build();
        return userRepository.save(user);
    }

    @Transactional
    public void saveVerificationTokenForUser(String token, User user) {
        log.info("Executing method [saveVerificationTokenForUser]");
        VerificationToken verificationToken = new VerificationToken(token,user);

        verificationTokenRepository.save(verificationToken);
    }

    @Transactional
    public void saveVerificationPasswordForUser(User user) {
        log.info("Executing method [saveVerificationPasswordForUser]");
        VerificationPassword verificationPassword = new VerificationPassword(user.getPassword(),user);

        verificationPasswordRepository.save(verificationPassword);
    }

    @Transactional
    public void verifyToken(String token) throws MarblException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        Calendar cal = Calendar.getInstance();

        if(Objects.isNull(verificationToken )) {
            throw new MarblException("User not Authorized.");
        } else if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            throw new MarblException("Token Expired.");
        }

        User user = verificationToken.getUser();
        user.setEnabled(Boolean.TRUE);
        userRepository.save(user);
    }

    @Transactional
    public String generateNewVerifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        String newToken = UUID.randomUUID().toString();

        verificationToken.setToken(newToken);
        verificationTokenRepository.save(verificationToken);

        return newToken;
    }

    @Transactional
    public User resetPassword(String email) {
        Optional<User> user = userRepository.findByRegistryContactEmail(email);
        if(user.isPresent()) {
            user.get().setPassword(RandomStringUtils.randomNumeric(8));
            return userRepository.save(user.get());
        }
        return null;
    }
}
