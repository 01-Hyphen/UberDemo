package com.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRegistrationEvent {
    private long userId;
    private String userName;
    private List<String> roles;
}
