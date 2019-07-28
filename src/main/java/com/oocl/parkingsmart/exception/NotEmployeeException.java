package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class NotEmployeeException extends SystemException {
    public NotEmployeeException() {
        super(HttpStatus.BAD_REQUEST,"wrong user name or password");
    }
}
