package com.driver.exception;

import com.driver.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DriverAlreadyHasBookingAssignException.class, EntityNotFoundException.class,NoBookingAvailableForDriverException.class, NoNearbyDriversAvailableException.class})
    public ResponseEntity<ErrorResponseDto> customExceptionHandler(Exception exception,HttpServletRequest httpServletRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMsg(exception.getMessage());
        errorResponseDto.setTimestamp(LocalDateTime.now());
        errorResponseDto.setPath(httpServletRequest.getServletPath());
        errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RideAlreadyAcceptedException.class)
    public ResponseEntity<ErrorResponseDto> rideAlreadyAcceptedExceptionHandler(Exception exception, HttpServletRequest httpServletRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMsg(exception.getMessage());
        errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
        errorResponseDto.setTimestamp(LocalDateTime.now());
        errorResponseDto.setPath(httpServletRequest.getServletPath());
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }
}

