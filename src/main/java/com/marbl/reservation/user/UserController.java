package com.marbl.reservation.user;

import com.marbl.reservation.booking.Booking;
import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.shared.SaveResponse;
import com.marbl.reservation.user.response.AllUserResponse;
import com.marbl.reservation.user.response.SingleUserResponse;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(tags = "User", description = "Retrieve all user", summary = "We are able to retrieve all user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AllUserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllUserResponse.class)))})
    public ResponseEntity<MarblResponse<List<User>>> getAllUser(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllUser] method of [UserController]");
        MarblResponse<List<User>> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        List<User> result = userService.getAllUser();

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "User", description = "Retrieve the selected user", summary = "We are able to retrieve the selected user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleUserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleUserResponse.class)))})
    public ResponseEntity<MarblResponse<User>> getUser(@PathVariable("id") Long userId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getReservation] method of [UserController]");
        MarblResponse<User> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        User result = userService.getUser(userId);

        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "User", description = "Save a new user", summary = "We are able to store a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> saveUser(@Valid @RequestBody User userRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveReservation] method of [UserController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        User result = userService.saveNewUser(userRequest);

        response.setData(result.getUserId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "User", description = "Update a user", summary = "We are able to update an already stored user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody Booking userRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateUser] method of [UserController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());

        User result = userService.updateReservation(userId,userRequest);
        response.setData(result.getUserId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "User", description = "Delete the selected reservation", summary = "We are able to delete the selected reservation by id")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> deleteUser(@PathVariable("id") Long userId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteReservation] method of [UserController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(),HttpStatus.OK.value());
        userService.deleteUser(userId);

        return ResponseEntity.ok(response);
    }
}
