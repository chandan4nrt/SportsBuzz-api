package com.chandan.sports.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Specific Custom Validation/Business Logic Failures
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle Any Other Unexpected Database or Server Failures
    public ResponseEntity<ErrorResponse> handleGeneralException (Exception ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected database or server error occurred.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON Request",
                "The provided JSON request body is invalid or malformed. Please check your syntax.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
