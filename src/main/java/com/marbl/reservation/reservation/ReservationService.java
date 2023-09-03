package com.marbl.reservation.reservation;

import com.marbl.reservation.exception.MarblException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private static final String NOT_FOUND = "Reservation is not found.";
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reservation getReservation(Long reservationId) throws MarblException {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        return reservation.get();
    }

    @Transactional
    public Reservation saveNewReservation(Reservation reservationRequest) {
        return reservationRepository.save(reservationRequest);
    }

    @Transactional
    public Reservation updateReservation(Long reservationId, Reservation reservationRequest) throws MarblException {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        BeanUtils.copyProperties(reservationRequest,reservation,"id");
        return reservationRepository.save(reservation.get());
    }
    @Transactional
    public void deleteReservation(Long reservationId) throws MarblException {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()) {
            throw new MarblException(NOT_FOUND);
        }
        reservationRepository.deleteById(reservationId);
    }


}
