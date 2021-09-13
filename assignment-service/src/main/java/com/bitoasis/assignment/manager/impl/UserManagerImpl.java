package com.bitoasis.assignment.manager.impl;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.enums.ResponseCode;
import com.bitoasis.assignment.exception.UserException;
import com.bitoasis.assignment.manager.UserManager;
import com.bitoasis.assignment.request.UserRegistrationRequest;
import com.bitoasis.assignment.response.UserRegistrationResponse;
import com.bitoasis.assignment.service.CheckEmailExistenceService;
import com.bitoasis.assignment.service.UserRegistrationService;
import com.bitoasis.assignment.util.Encryption;

public class UserManagerImpl implements UserManager {

    private UserRegistrationService userRegistrationService;
    private CheckEmailExistenceService checkEmailExistenceService;

    public UserManagerImpl(UserRegistrationService userRegistrationService,
                           CheckEmailExistenceService checkEmailExistenceService) {
        this.userRegistrationService = userRegistrationService;
        this.checkEmailExistenceService = checkEmailExistenceService;
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        Boolean isExist = checkEmailExistenceService.existsByEmail(request.getEmail());
        if (isExist)
            throw new UserException(ResponseCode.EMAIL_ALREADY_REGISTERED, ResponseCode.EMAIL_ALREADY_REGISTERED.getMessage() + " " + request.getEmail());
        UserBo userBo = userRegistrationService.register(UserBo.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(Encryption.encrypt(request.getPassword()))
                .build());
        return UserRegistrationResponse.builder()
                .email(userBo.getEmail())
                .name(userBo.getName())
                .build();
    }
}
