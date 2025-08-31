package com.booking.exception;

import com.booking.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoBookingsFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> customExceptionHandler(Exception exception, HttpServletRequest httpServletRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMsg(exception.getMessage());
        errorResponseDto.setStatus(HttpStatus.BAD_REQUEST);
        errorResponseDto.setTimestamp(LocalDateTime.now());
        errorResponseDto.setPath(httpServletRequest.getServletPath());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

}
