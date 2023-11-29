package com.marbl.reservation.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class MarblExceptionHandler {

        @ExceptionHandler(value = {MarblException.class})
        public ResponseEntity<Object> handleUserServiceException(MarblException ex, WebRequest request) {
            String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
            ExceptionMessage exceptionMessage = new ExceptionMessage(ex.getMessage(), requestUri);
            return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        @ExceptionHandler(value = {ExpiredJwtException.class})
        public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
            String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
            ExceptionMessage exceptionMessage = new ExceptionMessage(ex.getMessage(), requestUri);
            return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        /**
         * handlerOtherExceptions handles any unhandled exceptions.
         */
        @ExceptionHandler(value = {Exception.class})
        public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
            String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
            ExceptionMessage exceptionMessage = new ExceptionMessage("General System Error Please Retry Later.", requestUri);
            return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

