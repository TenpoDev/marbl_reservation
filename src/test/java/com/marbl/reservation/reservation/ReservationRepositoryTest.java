package com.marbl.reservation.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
                        .name("Lesson")
                        .reservedBy("Bill")
                        .build();

        entityManager.persist(reservation);
    }

    @Test
    void findById_thenReturnReservation() {
        Reservation found = reservationRepository.findById(1L).get();

        assertEquals("Private Lesson", found.getName());
    }
}