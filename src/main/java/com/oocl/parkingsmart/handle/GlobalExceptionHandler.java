package com.oocl.parkingsmart.handle;

import com.oocl.parkingsmart.exception.SystemException;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public ResponseEntity systemExceptionHandler(SystemException err){
        return new ResponseEntity<>(err.getMessage(), err.getErrorCode());
    }

    @ExceptionHandler(JDBCException.class)
    @ResponseBody
    public ResponseEntity jdbcExceptionHandler(JDBCException err){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.toString());
    }
}
