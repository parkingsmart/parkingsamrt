package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughCapacityException extends SystemException {
    public NotEnoughCapacityException(String parkingLotName) {
        super(HttpStatus.NOT_ACCEPTABLE, String.format("%s暂无位置", parkingLotName));
    }
}
