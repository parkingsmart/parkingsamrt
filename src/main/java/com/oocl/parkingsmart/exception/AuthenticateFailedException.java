package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class AuthenticateFailedException extends SystemException {
    public AuthenticateFailedException() {
        super(HttpStatus.BAD_REQUEST,"用户名或密码错误");
    }
}
