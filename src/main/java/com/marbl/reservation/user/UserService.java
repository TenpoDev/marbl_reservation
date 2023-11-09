package com.marbl.reservation.user;

import com.marbl.reservation.booking.Booking;
import com.marbl.reservation.exception.MarblException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String NOT_FOUND = "Booking is not found.";
    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) throws MarblException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        return user.get();
    }
    @Transactional
    public User saveNewUser(User userRequest) {
        return userRepository.save(userRequest);
    }
    @Transactional
    public User updateReservation(Long userId, Booking userRequest) throws MarblException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        BeanUtils.copyProperties(userRequest,user,"id");
        return userRepository.save(user.get());
    }

    @Transactional
    public void deleteUser(Long userId) throws MarblException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
}
