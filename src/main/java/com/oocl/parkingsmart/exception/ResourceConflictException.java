package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class ResourceConflictException extends SystemException {
    public ResourceConflictException(String message) {
        super(HttpStatus.CONFLICT,  String.format("[Conflict]: %s", message));
    }
}
