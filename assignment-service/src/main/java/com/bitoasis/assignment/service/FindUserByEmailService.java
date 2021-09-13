package com.bitoasis.assignment.service;

import com.bitoasis.assignment.bo.UserBo;

import java.util.Optional;

public interface FindUserByEmailService {

    Optional<UserBo> findByEmail(String email);
}
