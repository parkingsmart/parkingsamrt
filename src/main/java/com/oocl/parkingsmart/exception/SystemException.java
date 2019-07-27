package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class SystemException extends Exception {
    private HttpStatus errorCode;

    SystemException(HttpStatus errorCode){
        this.errorCode = errorCode;
    }

    SystemException(HttpStatus errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode(){
        return errorCode;
    }
}
