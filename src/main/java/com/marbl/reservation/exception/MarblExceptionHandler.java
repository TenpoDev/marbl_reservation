package com.marbl.reservation.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@ControllerAdvice
public class MarblExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MarblException.class)
    public ResponseEntity<MarblError> marblException(MarblException e, HttpServletRequest httpServletRequest) {
        log.error("Exception: ", e);
        MarblError error = new MarblError(HttpStatus.NOT_FOUND,e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
    }
}
