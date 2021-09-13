package com.bitoasis.assignment.repository;

import com.bitoasis.assignment.bo.UserBo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest implements UserRepository {

    private List<UserBo> list = new ArrayList<>();

    @Override
    public Boolean existsByEmail(String email) {
        Optional<UserBo> user = list.stream().filter(userBo -> email.equals(userBo.getEmail())).findFirst();
        if (user.isPresent())
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    @Override
    public UserBo save(UserBo userBo) {
        list.add(userBo);
        return userBo;
    }

    @Override
    public Optional<UserBo> findByEmail(String email) {
        Optional<UserBo> user = list.stream().filter(userBo -> email.equals(userBo.getEmail())).findFirst();
        return user;
    }
}
