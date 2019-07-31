package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class PayPasswordException extends SystemException {
    public PayPasswordException() {
        super(HttpStatus.BAD_REQUEST,"支付密码必须为6位数！");
    }
}
