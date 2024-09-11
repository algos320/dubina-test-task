package com.example.external;

import lombok.Getter;

public class ErrorCodeException  extends RuntimeException{
    @Getter
    private final int httpStatus;
    public ErrorCodeException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
