package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class InsufficientPointsException extends SystemException {
    public InsufficientPointsException() {
        super(HttpStatus.NOT_FOUND,"积分不足");
    }
}
