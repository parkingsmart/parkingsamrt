package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends SystemException{
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND,"用户不存在");
    }
}
