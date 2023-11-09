package com.marbl.reservation.login;

import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.registration.PasswordResetForm;
import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.shared.event.RegistrationResetPasswordEvent;
import com.marbl.reservation.user.User;
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

import java.time.OffsetDateTime;

@Slf4j
@RestController
@AllArgsConstructor
public class MarblController {

    private final MarblService marblService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping(value = "/public/v1/login")
    @Operation(tags = "Login", description = "Verify token", summary = "We are able to check if user is allowed using token.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> login(@RequestParam String token, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [verifyRegistration] method of [UserController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/api/v1/passwords")
    @Operation(tags = "Registration", description = "Reset Password", summary = "We are able reset the password so the user can proceed with the login.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<User>> resetPassword(@RequestBody PasswordResetForm passwordResetForm, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [resetPassword] method of [UserController]");
        MarblResponse<User> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());
        User data = marblService.resetPassword(passwordResetForm);
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
    }
}
