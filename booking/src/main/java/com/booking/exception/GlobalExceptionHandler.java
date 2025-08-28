package com.booking.exception;

import com.booking.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoBookingsFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> customExceptionHandler(Exception exception){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMsg(exception.getMessage());
        errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

}
