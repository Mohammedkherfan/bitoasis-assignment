package com.bitoasis.assignment.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {

    TECHNICAL_ERROR(-1, "Technical error"),

    SUCCESS(0, "Success"),

    EMAIL_ALREADY_REGISTERED(10, "Email already registered"),
    ERROR_CALLING_THIRD_PARTY(11, "Error during call third part apis"),
    UNDEFINED_COIN(12, "Undefined coin"),
    BAD_REQUEST(13, "Invalid request"),
    UNAUTHORIZED(14, "Failed to authenticate with the client server"),
    FORBIDDEN(15, "Client authenticated but does not have permission to access"),
    PRECONDITION_FAILED(16, "One or more conditions in the request header fields evaluated to false"),
    NOT_FOUND(17, "Requested resource does not exist"),
    INTERNAL_SERVER_ERROR(18, "Client server error"),
    SERVICE_UNAVAILABLE(19, " Requested service is not available"),
    INVALID_REQUEST(20, "Invalid request");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
