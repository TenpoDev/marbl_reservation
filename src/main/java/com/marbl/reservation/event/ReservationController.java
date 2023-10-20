package com.marbl.reservation.event;


import com.marbl.reservation.event.response.AllReservationResponse;
import com.marbl.reservation.event.response.SaveReservationResponse;
import com.marbl.reservation.event.response.SingleReservationResponse;
import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.shared.MarblResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(tags = "Reservation", description = "Retrieve all reservation", summary = "We are able to retrieve all reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AllReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllReservationResponse.class)))})
    public ResponseEntity<MarblResponse<List<Reservation>>> getAllReservation(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllReservation] method of [ReservationController]");
        MarblResponse<List<Reservation>> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        List<Reservation> result = reservationService.getAllReservation();

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Reservation", description = "Retrieve the selected reservation", summary = "We are able to retrieve the selected reservation by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleReservationResponse.class)))})
    public ResponseEntity<MarblResponse<Reservation>> getReservation(@PathVariable("id") Long reservationId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getReservation] method of [ReservationController]");
        MarblResponse<Reservation> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        Reservation result = reservationService.getReservation(reservationId);

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "Reservation", description = "Save a new reservation", summary = "We are able to store a new reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveReservationResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> saveReservation(@Valid @RequestBody Reservation reservationRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveReservation] method of [ReservationController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        Reservation result = reservationService.saveNewReservation(reservationRequest);

        response.setData(result.getReservationId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "Reservation", description = "Update a reservation", summary = "We are able to update an already stored reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveReservationResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> updateReservation(@PathVariable("id") Long reservationId, @Valid @RequestBody Reservation reservationRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateReservation] method of [ReservationController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());

        Reservation result = reservationService.updateReservation(reservationId,reservationRequest);
        response.setData(result.getReservationId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "Reservation", description = "Delete the selected reservation", summary = "We are able to delete the selected reservation by id")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse> deleteReservation(@PathVariable("id") Long reservationId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteReservation] method of [ReservationController]");
        MarblResponse<?> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        reservationService.deleteReservation(reservationId);

        return ResponseEntity.ok(response);
    }
}
