package com.booking.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entity,String key, String value){
        super(entity+" not found for "+key+" "+value);
    }
}
