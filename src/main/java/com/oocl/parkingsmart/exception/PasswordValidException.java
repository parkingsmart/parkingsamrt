package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class PasswordValidException extends SystemException {
    public PasswordValidException() {
        super(HttpStatus.BAD_REQUEST,"wrong user password");
    }
}
