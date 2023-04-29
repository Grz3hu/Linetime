package com.linetime.backend.exception;

public class TimelineNotFoundException extends RuntimeException{
    public TimelineNotFoundException(Integer id){
        super("Could not found timeline with id "+ id);
    }
}