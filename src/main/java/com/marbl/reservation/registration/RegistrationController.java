package com.marbl.reservation.registration;

import com.marbl.reservation.exception.MarblException;
import com.marbl.reservation.shared.SaveResponse;
import com.marbl.reservation.shared.event.RegistrationCompleteEvent;
import com.marbl.reservation.exception.MarblError;
import com.marbl.reservation.shared.MarblResponse;
import com.marbl.reservation.shared.event.RegistrationResendEvent;
import com.marbl.reservation.shared.event.RegistrationResetPasswordEvent;
import com.marbl.reservation.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping
    @Operation(tags = "Registration", description = "Save a new user", summary = "We are able to store a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaveResponse.class)))})
    public ResponseEntity<MarblResponse<Long>> registration(@Valid @RequestBody UserRegistrationForm userRequest, HttpServletRequest httpServletRequest) {
        log.info("Inside [saveReservation] method of [UserController]");
        MarblResponse<Long> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());
        User result = registrationService.saveNewUser(userRequest);
        response.setData(result.getUserId());
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(result, applicationUrl(httpServletRequest)));

        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/verify")
    @Operation(tags = "Registration", description = "Verify token", summary = "We are able to check if user is allowed using token.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> verifyRegistration(@RequestParam String token, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [verifyRegistration] method of [UserController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());
        registrationService.verifyToken(token);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/verify/resend")
    @Operation(tags = "Registration", description = "Resend Verify token", summary = "We are able resend the token so the user can proceed with verification.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> resendVerifyRegistration(@RequestParam("oldToken") String token, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [resendVerifyRegistration] method of [UserController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());
        applicationEventPublisher.publishEvent(new RegistrationResendEvent(token, applicationUrl(httpServletRequest)));

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/reset")
    @Operation(tags = "Registration", description = "Reset Password", summary = "We are able reset the password so the user can proceed with the login.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MarblError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarblResponse.class)))})
    public ResponseEntity<MarblResponse<Object>> resetPassword(@RequestBody PasswordResetForm passwordResetForm, HttpServletRequest httpServletRequest) throws MarblException {
        log.info("Inside [resetPassword] method of [UserController]");
        MarblResponse<Object> response = new MarblResponse<>(OffsetDateTime.now(),httpServletRequest.getServletPath(), HttpStatus.OK.value());
        applicationEventPublisher.publishEvent(new RegistrationResetPasswordEvent(passwordResetForm.getEmail(), applicationUrl(httpServletRequest)));

        return ResponseEntity.ok(response);
    }



    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
    }

}
