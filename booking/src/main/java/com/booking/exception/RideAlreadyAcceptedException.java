package com.booking.exception;

import com.booking.entity.Rider;

public class RideAlreadyAcceptedException extends RuntimeException{

    public RideAlreadyAcceptedException(String msg){
        super(msg);
    }

}
