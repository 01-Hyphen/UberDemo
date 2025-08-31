package com.driver.exception;

import com.driver.constants.RideStatus;

public class RideAlreadyAcceptedException extends RuntimeException{
    public RideAlreadyAcceptedException(String msg){
        super(msg);

    }
}
