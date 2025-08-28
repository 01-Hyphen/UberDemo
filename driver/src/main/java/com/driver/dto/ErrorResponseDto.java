package com.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    String msg;
    HttpStatus status;
}
