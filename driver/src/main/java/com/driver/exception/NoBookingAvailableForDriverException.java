package com.driver.exception;

public class NoBookingAvailableForDriverException extends RuntimeException{
    public NoBookingAvailableForDriverException(){
        super("You have no bookings as of now");
    }
}
