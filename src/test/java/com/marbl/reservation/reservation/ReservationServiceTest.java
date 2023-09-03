package com.marbl.reservation.reservation;

import com.marbl.reservation.exception.MarblException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @MockBean
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        Reservation reservation =
                Reservation.builder()
                        .id(5L)
                        .name("Lesson")
                        .reservedBy("Bill")
                        .build();

        Mockito.when(reservationRepository.findById(5L)).thenReturn(Optional.of(reservation));

    }

    @Test
    @DisplayName("Get Reservation By Id")
    void getReservation_usingInputId() throws MarblException {
        Long reservationId = 5L;
        Reservation found = reservationService.getReservation(reservationId);

        assertEquals(reservationId, found.getId());
    }


}