package com.marbl.reservation.booking;

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
public class BookingService {

    private final BookingRepository bookingRepository;
    private static final String NOT_FOUND = "Booking is not found.";
    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Booking getBooking(Long reservationId) throws MarblException {
        Optional<Booking> reservation = bookingRepository.findById(reservationId);
        if(reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        return reservation.get();
    }

    @Transactional
    public Booking saveNewBooking(Booking bookingRequest) {
        return bookingRepository.save(bookingRequest);
    }

    @Transactional
    public Booking updateBooking(Long reservationId, Booking bookingRequest) throws MarblException {
        Optional<Booking> reservation = bookingRepository.findById(reservationId);
        if(reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        BeanUtils.copyProperties(bookingRequest,reservation,"id");
        return bookingRepository.save(reservation.get());
    }
    @Transactional
    public void deleteBooking(Long reservationId) throws MarblException {
        Optional<Booking> reservation = bookingRepository.findById(reservationId);
        if(reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        bookingRepository.deleteById(reservationId);
    }


}
