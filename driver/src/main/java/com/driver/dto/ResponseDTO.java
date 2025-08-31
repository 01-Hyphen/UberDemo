package com.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class ResponseDTO {
    String msg;
    HttpStatus httpStatus;
}
