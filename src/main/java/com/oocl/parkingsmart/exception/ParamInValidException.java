package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class ParamInValidException extends SystemException{
    ParamInValidException() {
        super(HttpStatus.BAD_REQUEST,"传入参数有误");
    }
}
