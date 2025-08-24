package com.driver.exception;

public class NoNearbyDriversAvailableException extends RuntimeException{
    public NoNearbyDriversAvailableException(){
        super("No drivers available within 5 km area");
    }
}
