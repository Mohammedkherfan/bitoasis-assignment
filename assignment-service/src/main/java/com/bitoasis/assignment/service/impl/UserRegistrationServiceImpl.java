package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.repository.UserRepository;
import com.bitoasis.assignment.service.UserRegistrationService;

public class UserRegistrationServiceImpl implements UserRegistrationService {

    private UserRepository userRepository;

    public UserRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserBo register(UserBo userBo) {
        UserBo user = userRepository.save(userBo);
        return user;
    }
}
