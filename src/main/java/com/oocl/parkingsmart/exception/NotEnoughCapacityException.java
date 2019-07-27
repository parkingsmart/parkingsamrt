package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughCapacityException extends SystemException {
    public NotEnoughCapacityException(String parkingLotName) {
        super(HttpStatus.NOT_ACCEPTABLE, String.format("the %s parking lot is not enough capacity", parkingLotName));
    }
}
