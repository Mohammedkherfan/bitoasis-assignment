package com.bitoasis.assignment.api.impl;

import com.bitoasis.assignment.api.UserController;
import com.bitoasis.assignment.manager.UserManager;
import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.response.UserRegistrationResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "User Api")
public class UserControllerImpl implements UserController {

    private UserManager userManager;

    @Autowired
    public UserControllerImpl(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public UserRegistrationResponse register(@RequestBody @Valid UserRegistrationRequest request) {
        return userManager.register(request);
    }
}
