package com.zenhome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDurationException extends RuntimeException {

    public InvalidDurationException(String msg) {
        super(msg + " is invalid duration valid duration is like 24h");
    }
}
