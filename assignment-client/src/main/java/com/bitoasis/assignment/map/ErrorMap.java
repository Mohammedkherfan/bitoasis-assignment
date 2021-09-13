package com.bitoasis.assignment.map;

import com.bitoasis.assignment.enums.ResponseCode;
import org.springframework.http.HttpStatus;

import static java.util.Objects.isNull;

public class ErrorMap {

    public static ResponseCode getResponseCode(HttpStatus statusCode) {
        if (isNull(statusCode))
            return ResponseCode.ERROR_CALLING_THIRD_PARTY;

        switch (statusCode) {
            case BAD_REQUEST:
                return ResponseCode.BAD_REQUEST;
            case UNAUTHORIZED:
                return ResponseCode.UNAUTHORIZED;
            case FORBIDDEN:
                return ResponseCode.FORBIDDEN;
            case NOT_FOUND:
                return ResponseCode.NOT_FOUND;
            case PRECONDITION_FAILED:
                return ResponseCode.PRECONDITION_FAILED;
            case INTERNAL_SERVER_ERROR:
                return ResponseCode.INTERNAL_SERVER_ERROR;
            case SERVICE_UNAVAILABLE:
                return ResponseCode.SERVICE_UNAVAILABLE;
            default:
                return ResponseCode.ERROR_CALLING_THIRD_PARTY;
        }
    }
}
