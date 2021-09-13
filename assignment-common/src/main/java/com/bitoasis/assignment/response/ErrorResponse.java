package com.bitoasis.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private Integer responseCode;
    private String responseMessage;
    private LocalDateTime responseTime;
}
