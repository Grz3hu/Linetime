package com.linetime.backend.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String id){
        super("Could not found role named "+ id);
    }
}