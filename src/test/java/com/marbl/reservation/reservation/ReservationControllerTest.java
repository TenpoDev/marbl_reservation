package com.marbl.reservation.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder().reservationId(1L).reservationTitle("Math Lesson").reservedBy("Luis Alberic").build();
    }

    @Test
    void getAllReservation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/reservations");

        Reservation reservation = Reservation.builder().reservationId(1L).reservationTitle("Math Lesson").reservedBy("Luis Alberic").build();
        Reservation reservation2 = Reservation.builder().reservationId(2L).reservationTitle("Gym Lesson").reservedBy("Althea Alberic").build();
        List<Reservation> allReservation = Arrays.asList(reservation, reservation2);
        Mockito.when(reservationService.getAllReservation()).thenReturn(allReservation);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("jakarta.servlet.http.HttpServletRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result[0].name").value(allReservation.get(0).getReservationTitle()));

    }

    @Test
    void getReservation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/reservations");

        Reservation reservation = Reservation.builder().reservationId(1L).reservationTitle("Math Lesson").reservedBy("Luis Alberic").build();
        Mockito.when(reservationService.getReservation(1L)).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("jakarta.servlet.http.HttpServletRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.id").value(reservation.getReservationId()));
    }

    @Test
    void saveReservation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/reservations");

        Reservation reservation = Reservation.builder().reservationTitle("Math Lesson").reservedBy("Luis Alberic").build();

        Mockito.when(reservationService.saveNewReservation(reservation)).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("jakarta.servlet.http.HttpServletRequest", request)
                        .content("{\"name\":\"Math Lesson\",\"reservedBy\":\"Luis Alberic\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void updateReservation() throws Exception {
        Long reservationId=1L;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/reservations");

        Reservation reservation = Reservation.builder().reservationId(1L).reservationTitle("Math Lesson").reservedBy("Luis Alberic").build();
        Mockito.when(reservationService.updateReservation(1L, reservation)).thenReturn(reservation);

        mockMvc.perform((MockHttpServletRequestBuilder) MockMvcRequestBuilders
                        .put("/reservations/{id}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("jakarta.servlet.http.HttpServletRequest", request)
                        .content("{\"id\":\"1\",\"name\":\"Math Lesson\",\"reservedBy\":\"Luis Alberic\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteReservation() throws Exception {
        Long reservationId = 1L;

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/reservations");

        Reservation reservation = Reservation.builder().reservationId(1L).reservationTitle("Math Lesson").reservedBy("Luis Alberic").build();
        Mockito.doNothing().when(reservationService).deleteReservation(reservationId);

        mockMvc.perform((MockHttpServletRequestBuilder) MockMvcRequestBuilders
                        .delete("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("id",1L)
                        .requestAttr("jakarta.servlet.http.HttpServletRequest", request)
                        .content("{\"name\":\"Math Lesson\",\"reservedBy\":\"Luis Alberic\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}