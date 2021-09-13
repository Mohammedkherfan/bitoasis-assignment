package com.bitoasis.assignment.manager;

import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.response.UserRegistrationResponse;

public interface UserManager {

    UserRegistrationResponse register(UserRegistrationRequest request);
}
