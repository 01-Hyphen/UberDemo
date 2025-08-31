package com.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ResponseDTO {
   private String msg;
   private HttpStatus httpStatus;

}
