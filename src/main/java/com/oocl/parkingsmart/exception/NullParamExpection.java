package com.oocl.parkingsmart.exception;

import org.springframework.http.HttpStatus;

public class NullParamExpection extends SystemException {
    NullParamExpection() {
        super(HttpStatus.BAD_REQUEST,"参数不能为空");
    }
}
