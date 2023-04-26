package com.linetime.backend.payload;

import lombok.Data;

@Data
public class IsAdminDto {
    boolean isAdmin;

    public IsAdminDto(boolean isAdmin){
        this.isAdmin=isAdmin;
    }
}
