package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class OverNumberExpection extends SystemException{

    public OverNumberExpection() {
        super(HttpStatus.BAD_REQUEST,"Can't manage more than 3 parking lots");
    }

}
