package com.linetime.backend.exception;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String username){
        super("Email " + username + "is already used!");
    }
}