package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class PromotionIsNotExistException extends SystemException{
    public PromotionIsNotExistException() {
        super(HttpStatus.BAD_REQUEST,"Offer does not exist");
    }
}
