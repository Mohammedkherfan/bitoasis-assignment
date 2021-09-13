package com.bitoasis.assignment.mapping;

import com.bitoasis.assignment.bo.UserBo;
import com.bitoasis.assignment.entity.UserEntity;

public interface UserMapping {

    UserBo toBo(UserEntity entity);

    UserEntity toEntity(UserBo bo);
}
