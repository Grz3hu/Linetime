package com.linetime.backend.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id){
        super("Could not found user with id "+ id);
    }
    public UserNotFoundException(String name){
        super("Could not found user with name "+ name);
    }
}