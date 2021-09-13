package com.bitoasis.assignment.repository.impl;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.entity.UserEntity;
import com.bitoasis.assignment.jpa.UserJpaRepository;
import com.bitoasis.assignment.mapping.UserMapping;
import com.bitoasis.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserMapping userMapping;

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public UserBo save(UserBo userBo) {
        UserEntity userEntity = userJpaRepository.save(userMapping.toEntity(userBo));
        return userMapping.toBo(userEntity);
    }

    @Override
    public Optional<UserBo> findByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email);
        if (isNull(userEntity))
            return Optional.empty();
        else
            return Optional.of(userMapping.toBo(userEntity));
    }
}
