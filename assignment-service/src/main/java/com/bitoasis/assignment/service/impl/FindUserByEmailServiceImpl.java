package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.repository.UserRepository;
import com.bitoasis.assignment.service.FindUserByEmailService;

import java.util.Optional;

public class FindUserByEmailServiceImpl implements FindUserByEmailService {

    private UserRepository userRepository;

    public FindUserByEmailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserBo> findByEmail(String email) {
        Optional<UserBo> user = userRepository.findByEmail(email);
        return user;
    }
}
