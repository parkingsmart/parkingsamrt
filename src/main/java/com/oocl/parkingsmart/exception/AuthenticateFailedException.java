package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class AuthenticateFailedException extends SystemException {
    public AuthenticateFailedException() {
        super(HttpStatus.BAD_REQUEST,"wrong user name or password");
    }
}
