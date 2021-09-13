package com.bitoasis.assignment.mapping.impl;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.entity.UserEntity;
import com.bitoasis.assignment.mapping.UserMapping;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMappingImpl implements UserMapping {

    @Override
    public UserBo toBo(UserEntity entity) {
        UserBo bo = new UserBo();
        BeanUtils.copyProperties(entity, bo);
        return bo;
    }

    @Override
    public UserEntity toEntity(UserBo bo) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(bo, entity);
        return entity;
    }
}
