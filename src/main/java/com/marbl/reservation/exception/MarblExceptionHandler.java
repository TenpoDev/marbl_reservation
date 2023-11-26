package com.marbl.reservation.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class MarblExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MarblError> generalException(Exception e, HttpServletRequest httpServletRequest) {
        log.error("Exception: ", e);
        MarblError error = new MarblError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(error);
    }

    @ExceptionHandler(MarblException.class)
    public ResponseEntity<MarblError> marblException(MarblException e, HttpServletRequest httpServletRequest) {
        log.error("Exception: ", e);
        MarblError error = new MarblError(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MarblError> authenticationException(AuthenticationException e, HttpServletRequest httpServletRequest) {
        log.error("Exception: ", e);
        MarblError error = new MarblError(HttpStatus.UNAUTHORIZED, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
