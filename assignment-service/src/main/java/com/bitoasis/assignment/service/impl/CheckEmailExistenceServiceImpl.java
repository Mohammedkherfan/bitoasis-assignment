package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.repository.UserRepository;
import com.bitoasis.assignment.service.CheckEmailExistenceService;

public class CheckEmailExistenceServiceImpl implements CheckEmailExistenceService {

    private UserRepository userRepository;

    public CheckEmailExistenceServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean existsByEmail(String email) {
        Boolean isExist = userRepository.existsByEmail(email);
        return isExist;
    }
}
