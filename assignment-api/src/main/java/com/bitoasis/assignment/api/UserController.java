package com.bitoasis.assignment.api;

import com.bitoasis.assignment.constant.Constant;
import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.response.UserRegistrationResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping
public interface UserController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/" + Constant.REGISTER, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to register user", notes = "This method used when want to register user")
    @ApiParam(value = "Parameter of operation", name = "userRegistrationRequest")
    UserRegistrationResponse register(UserRegistrationRequest request);
}
