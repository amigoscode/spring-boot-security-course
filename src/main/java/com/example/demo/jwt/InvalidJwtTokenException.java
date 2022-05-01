package com.example.demo.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {
    public InvalidJwtTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
