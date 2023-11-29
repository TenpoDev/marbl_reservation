package com.marbl.reservation.login;

import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registration.PasswordResetForm;
import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.user.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.OffsetDateTime;

@Slf4j
@RestController
@AllArgsConstructor
public class MarblController {

    private final MarblService marblService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping(value = "/public/v1/login")
    @Operation(tags = "Login", description = "Verify token", summary = "We are able to check if user is allowed using token.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [login] method of [MarblController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());
        String token =  marblService.login(loginRequest);
        response.setData(token);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/api/v1/passwords")
    @Operation(tags = "Password", description = "Reset Password", summary = "We are able reset the password so the user can proceed with the login.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<User>> resetPassword(@RequestBody PasswordResetForm passwordResetForm, Principal connectedUser, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [resetPassword] method of [MarblController]");
        MarblResponse<User> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.CREATED.value());
        User data = marblService.changePassword(passwordResetForm, connectedUser);
        response.setData(data);

        return ResponseEntity.ok(response);
    }

//    private String applicationUrl(HttpServletRequest httpServletRequest) {
//        return "http://"+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
//    }
}
