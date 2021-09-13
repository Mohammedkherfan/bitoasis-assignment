package com.bitoasis.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegistrationResponse {

    private String name;
    private String email;

}
