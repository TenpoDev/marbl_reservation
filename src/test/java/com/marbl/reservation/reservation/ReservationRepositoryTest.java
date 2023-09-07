package com.marbl.reservation.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Reservation reservation =
                Reservation.builder()
                        .reservationTitle("Private Lesson")
                        .reservationReservedById("Bill")
                        .build();

        entityManager.persist(reservation);
    }

    @Test
    void findAll_thenReturnReservationsList() {
        List<Reservation> found = reservationRepository.findAll();

        assertEquals("Private Lesson", found.get(0).getReservationTitle());
    }

    @Test
    void findById_thenReturnReservation() {
        Reservation found = reservationRepository.findById(1L).get();

        assertEquals("Private Lesson", found.getReservationTitle());
    }

    @Test
    void saveNewReservation_thenReturnReservation() {
        Reservation found = reservationRepository.save(Reservation.builder().reservationTitle("Gym").reservationReservedById("Linus").build());

        assertTrue(reservationRepository.existsById(found.getReservationId()));
    }

    @Test
    @Disabled
    void updateReservation_thenReturnReservation() {
        Reservation found = reservationRepository.findById(1L).get();

        found.setReservationTitle("Gym");
        found.setReservationReservedById("Luis");

        found = reservationRepository.save(found);

        assertEquals(found.getReservationTitle(), "Gym");
    }

    @Test
    void deleteById_thenReturnVoid() {
        reservationRepository.deleteById(1L);

        assertFalse(reservationRepository.findById(1L).isPresent());
    }
}