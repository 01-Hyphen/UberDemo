package com.driver.exception;

public class DriverAlreadyHasBookingAssignException extends RuntimeException{
    public DriverAlreadyHasBookingAssignException(String bookingId){
        super("Driver has already booking assign as :"+bookingId);
    }
}
