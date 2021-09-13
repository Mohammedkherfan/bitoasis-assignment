package com.bitoasis.assignment.exception;

import com.bitoasis.assignment.enums.ResponseCode;
import lombok.Getter;

@Getter
public class CoinException extends RuntimeException  {

    private ResponseCode responseCode;

    public CoinException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
