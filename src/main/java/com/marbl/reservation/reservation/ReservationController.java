package com.marbl.reservation.reservation;


import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
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
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(tags = "Reservation", description = "Retrieve all reservation", summary = "We are able to retrieve all reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)))})
    public ResponseEntity<ReservationResponse> getAllReservation(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllReservation] method of [ReservationController]");
        ReservationResponse response = new ReservationResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        List<Reservation> result = reservationService.getAllReservation();

        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Reservation", description = "Retrieve the selected reservation", summary = "We are able to retrieve the selected reservation by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationGetResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationGetResponse.class)))})
    public ResponseEntity<ReservationGetResponse> getReservation(@PathVariable("id") Long reservationId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getReservation] method of [ReservationController]");
        ReservationGetResponse response = new ReservationGetResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        Reservation result = reservationService.getReservation(reservationId);

        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "Reservation", description = "Save a new reservation", summary = "We are able to store a new reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationPostResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationPostResponse.class)))})
    public ResponseEntity<ReservationPostResponse> saveReservation(@Valid @RequestBody Reservation reservationRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveReservation] method of [ReservationController]");
        ReservationPostResponse response = new ReservationPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        Reservation result = reservationService.saveNewReservation(reservationRequest);

        response.setReservationId(result.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "Reservation", description = "Update a reservation", summary = "We are able to update an already stored reservation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationPostResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationPostResponse.class)))})
    public ResponseEntity<ReservationPostResponse> updateReservation(@PathVariable("id") Long reservationId, @Valid @RequestBody Reservation reservationRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateReservation] method of [ReservationController]");
        ReservationPostResponse response = new ReservationPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());

        Reservation result = reservationService.updateReservation(reservationId,reservationRequest);
        response.setReservationId(result.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "Reservation", description = "Delete the selected reservation", summary = "We are able to delete the selected reservation by id")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationGetResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationGetResponse.class)))})
    public ResponseEntity<ReservationGetResponse> deleteReservation(@PathVariable("id") Long reservationId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteReservation] method of [ReservationController]");
        ReservationGetResponse response = new ReservationGetResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        reservationService.deleteReservation(reservationId);

        return ResponseEntity.ok(response);
    }
}
