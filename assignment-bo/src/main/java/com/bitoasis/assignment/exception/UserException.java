package com.bitoasis.assignment.exception;

import com.bitoasis.assignment.enums.ResponseCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private ResponseCode responseCode;

    public UserException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
