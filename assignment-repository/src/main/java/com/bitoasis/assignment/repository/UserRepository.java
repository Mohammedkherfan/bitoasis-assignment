package com.bitoasis.assignment.repository;

import com.bitoasis.assignment.bo.UserBo;

import java.util.Optional;

public interface UserRepository {

    Boolean existsByEmail(String email);

    UserBo save(UserBo userBo);

    Optional<UserBo> findByEmail(String email);
}
