package com.marbl.reservation.reservation;

import com.marbl.reservation.exception.MarblException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @MockBean
    private ReservationRepository reservationRepository;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder().reservationId(5L).reservationTitle("Lesson").reservationReservedById("Bill").build();
    }

    @Test
    @DisplayName("Fetch All Stored Reservation")
    void getReservation_allReservation() {
        Mockito.when(reservationRepository.findAll()).thenReturn(Collections.singletonList(reservation));

        List<Reservation> found = reservationService.getAllReservation();

        assertEquals(found.get(0).getReservationTitle(), reservation.getReservationTitle());
    }

    @Test
    @DisplayName("Get Reservation By Id")
    void getReservation_usingInputId() throws MarblException {
        Long reservationId = 5L;
        Mockito.when(reservationRepository.findById(5L)).thenReturn(Optional.of(reservation));

        Reservation found = reservationService.getReservation(reservationId);
        reservationService = new ReservationService(reservationRepository);
        assertEquals(reservationId, found.getReservationId());
    }

    @Test
    @DisplayName("Store New Reservation")
    void saveReservation_usingNewRequest() {
        Reservation reservationRequest = Reservation.builder().reservationTitle("Lesson").reservationReservedById("Bill").build();

        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);

        Reservation found = reservationService.saveNewReservation(reservationRequest);
        assertEquals(reservation.getReservationTitle(), found.getReservationTitle());
    }

    @Test
    void saveReservation_updateStoredReservationFindById() throws MarblException {
        Long reservationId = 5L;
        Reservation reservationRequest = Reservation.builder().reservationTitle("Lesson").reservationReservedById("Bill").build();
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);
        Mockito.when(reservationRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(reservation));

        Reservation found = reservationService.updateReservation(reservationId, reservationRequest);

        reservationService = new ReservationService(reservationRepository);
        assertEquals(reservation.getReservationTitle(), found.getReservationTitle());
    }

    @Test
    void deleteReservation_usingId() throws MarblException {
        Long reservationId = 1L;

        Mockito.when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        reservationService.deleteReservation(reservationId);
        Mockito.verify(reservationRepository).deleteById(reservationId);

    }

}