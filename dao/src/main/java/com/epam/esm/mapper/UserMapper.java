package com.epam.esm.mapper;

import com.epam.esm.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(rs.getLong("user_id"));
        user.setMail(rs.getString("user_mail"));
        user.setNickname(rs.getString("user_nickname"));
        user.setPassword(rs.getString("user_password"));
        user.setBalance(rs.getBigDecimal("user_balance"));
        return user;
    }
}
