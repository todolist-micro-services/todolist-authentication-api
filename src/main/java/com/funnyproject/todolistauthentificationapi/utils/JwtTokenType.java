package com.funnyproject.todolistauthentificationapi.utils;

import java.time.LocalDateTime;

public class JwtTokenType {

    public void setJwtValue(String value) {
        this.jwtValue = value;
    }

    public void setExpirationDate(LocalDateTime time) {
        this.expirationDate = time;
    }

    public String getJwtValue() {
        return this.jwtValue;
    }

    public LocalDateTime getExpirationDate() {
        return this.expirationDate;
    }

    private LocalDateTime expirationDate;
    private String jwtValue;
}

