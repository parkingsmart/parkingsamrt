package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class PasswordValidException extends SystemException {
    public PasswordValidException() {
        super(HttpStatus.BAD_REQUEST,"用户密码错误");
    }
}
