package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends SystemException {
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND,"所需资源不存在");
    }
}
