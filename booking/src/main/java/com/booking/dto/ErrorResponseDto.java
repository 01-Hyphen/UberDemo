package com.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    String msg;
    HttpStatus status;
    LocalDateTime timestamp;
    String path;
}
