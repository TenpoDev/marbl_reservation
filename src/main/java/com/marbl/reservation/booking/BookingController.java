package com.marbl.reservation.booking;


import com.marbl.reservation.booking.response.AllBookingResponse;
import com.marbl.reservation.booking.response.SingleBookingResponse;
import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.shared.SaveResponse;
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
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @Operation(tags = "Booking", description = "Retrieve all bookings.", summary = "We are able to retrieve all bookings.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AllBookingResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllBookingResponse.class)))})
    public ResponseEntity<MarblResponse<List<Booking>>> getAllBookings(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllBooking] method of [BookingController]");
        MarblResponse<List<Booking>> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        List<Booking> result = bookingService.getAllBookings();

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "Booking", description = "Retrieve the selected booking.", summary = "We are able to retrieve the selected booking by id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleBookingResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleBookingResponse.class)))})
    public ResponseEntity<MarblResponse<Booking>> getBooking(@PathVariable("id") Long bookingId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getBooking] method of [BookingController]");
        MarblResponse<Booking> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        Booking result = bookingService.getBooking(bookingId);

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "Booking", description = "Save a new booking.", summary = "We are able to store a new booking.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> saveBooking(@Valid @RequestBody Booking bookingRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveBooking] method of [BookingController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        Booking result = bookingService.saveNewBooking(bookingRequest);

        response.setData(result.getBookingId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "Booking", description = "Update a booking.", summary = "We are able to update an already stored booking.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> updateBooking(@PathVariable("id") Long bookingId, @Valid @RequestBody Booking bookingRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateBooking] method of [BookingController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());

        Booking result = bookingService.updateBooking(bookingId, bookingRequest);
        response.setData(result.getBookingId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "Booking", description = "Delete the selected booking.", summary = "We are able to delete the selected booking by id.")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> deleteReservation(@PathVariable("id") Long bookingId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteBooking] method of [BookingController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        bookingService.deleteBooking(bookingId);

        return ResponseEntity.ok(response);
    }
}
