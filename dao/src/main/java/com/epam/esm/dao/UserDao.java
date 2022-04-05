package com.epam.esm.dao;

import com.epam.esm.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<UserEntity> readAll(long page, long pageSize);

    Optional<UserEntity> readById(long id);

    Optional<UserEntity> readByNickname(String nickname);

    Optional<UserEntity> readByMail(String mail);
}
