package com.marbl.reservation.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
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
                        .name("Private Lesson")
                        .reservedBy("Bill")
                        .build();

        entityManager.persist(reservation);
    }

    @Test
    void findAll_thenReturnReservationsList() {
        List<Reservation> found = reservationRepository.findAll();

        assertEquals("Private Lesson", found.get(0).getName());
    }

    @Test
    void findById_thenReturnReservation() {
        Reservation found = reservationRepository.findById(1L).get();

        assertEquals("Private Lesson", found.getName());
    }

    @Test
    void saveNewReservation_thenReturnReservation() {
        Reservation found = reservationRepository.save(Reservation.builder().name("Gym").reservedBy("Linus").build());

        assertTrue(reservationRepository.existsById(found.getId()));
    }

    @Test
    void updateReservation_thenReturnReservation() {
        Optional<Reservation> found = reservationRepository.findById(1L);

        Reservation foundDto = found.get();
        foundDto.setName("Gym");
        foundDto.setReservedBy("Luis");

       foundDto = reservationRepository.save(foundDto);

        assertEquals(foundDto.getName(), "Gym");
    }

    @Test
    void deleteById_thenReturnVoid() {
        reservationRepository.deleteById(1L);

        assertFalse(reservationRepository.findById(1L).isPresent());
    }
}