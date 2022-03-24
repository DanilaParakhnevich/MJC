package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    private static final String READ_ALL = "select u.id as user_id, u.mail as user_mail, u.nickname as user_nickname, " +
            "u.password as user_password, u.balance as user_balance " +
            "from \"user\" as u order by u.id limit ? offset ?";
    private static final String READ_BY_ID = "select u.id, u.mail, u.nickname, u.password, u.balance " +
            "from \"user\" u where u.id = ?";
    private static final String READ_BY_NICKNAME = "select u.id, u.mail, u.nickname, u.password, u.balance " +
            "from \"user\" u where u.nickname = ?";
    private static final String READ_BY_MAIL = "select u.id, u.mail, u.nickname, u.password, u.balance " +
            "from \"user\" u where mail = ?";
    JdbcTemplate jdbcTemplate;
    UserMapper userMapper;

    @Override
    public List<UserEntity> readAll(long page, long pageSize) {
        return jdbcTemplate.query(READ_ALL, userMapper, pageSize, (page - 1) * pageSize);
    }

    @Override
    public Optional<UserEntity> readById(long id) {
        List<UserEntity> users = jdbcTemplate.query(READ_BY_ID, userMapper, id);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public Optional<UserEntity> readByNickname(String nickname) {
        List<UserEntity> users = jdbcTemplate.query(READ_BY_NICKNAME, userMapper, nickname);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public Optional<UserEntity> readByMail(String mail) {
        List<UserEntity> users = jdbcTemplate.query(READ_BY_MAIL, userMapper, mail);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
