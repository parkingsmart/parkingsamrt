package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class ResourceConflictException extends SystemException {
    ResourceConflictException(String message) {
        super(HttpStatus.CONFLICT,  String.format("[Conflict]: %s", message));
    }
}
