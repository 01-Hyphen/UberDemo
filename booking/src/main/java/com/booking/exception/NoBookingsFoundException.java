package com.booking.exception;

public class NoBookingsFoundException extends RuntimeException {
    public NoBookingsFoundException(String msg){
        super(msg);
    }
}
