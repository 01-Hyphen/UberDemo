package com.driver.exception;

import com.driver.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DriverAlreadyHasBookingAssignException.class, EntityNotFoundException.class,NoBookingAvailableForDriverException.class, NoNearbyDriversAvailableException.class})
    public ResponseEntity<ErrorResponseDto> customExceptionHandler(Exception exception){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMsg(exception.getMessage());
        errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

}
