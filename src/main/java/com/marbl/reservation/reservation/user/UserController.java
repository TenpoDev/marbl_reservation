package com.marbl.reservation.reservation.user;

import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.reservation.Reservation;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(tags = "User", description = "Retrieve all user", summary = "We are able to retrieve all user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))})
    public ResponseEntity<UserResponse> getAllUser(HttpServletRequest httpServletRequest) {
        log.info("Inside [getAllUser] method of [UserController]");
        UserResponse response = new UserResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        List<User> result = userService.getAllUser();

        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    @Operation(tags = "User", description = "Retrieve the selected user", summary = "We are able to retrieve the selected user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserGetResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserGetResponse.class)))})
    public ResponseEntity<UserGetResponse> getUser(@PathVariable("id") Long userId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [getReservation] method of [UserController]");
        UserGetResponse response = new UserGetResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        User result = userService.getUser(userId);

        response.setResult(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(tags = "User", description = "Save a new user", summary = "We are able to store a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserPostResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPostResponse.class)))})
    public ResponseEntity<UserPostResponse> saveUser(@Valid @RequestBody User userRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveReservation] method of [UserController]");
        UserPostResponse response = new UserPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        User result = userService.saveNewUser(userRequest);

        response.setUserId(result.getUserId());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    @Operation(tags = "User", description = "Update a user", summary = "We are able to update an already stored user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserPostResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPostResponse.class)))})
    public ResponseEntity<UserPostResponse> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody Reservation userRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [updateUser] method of [UserController]");
        UserPostResponse response = new UserPostResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());

        User result = userService.updateReservation(userId,userRequest);
        response.setUserId(result.getUserId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(tags = "User", description = "Delete the selected reservation", summary = "We are able to delete the selected reservation by id")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))})
    public ResponseEntity<UserGetResponse> deleteUser(@PathVariable("id") Long userId, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [deleteReservation] method of [UserController]");
        UserGetResponse response = new UserGetResponse();
        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        response.setPath(httpServletRequest.getServletPath());
        userService.deleteUser(userId);

        return ResponseEntity.ok(response);
    }
}
