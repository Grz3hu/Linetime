package com.linetime.backend.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Integer id){
        super("Could not found event with id "+ id);
    }
}