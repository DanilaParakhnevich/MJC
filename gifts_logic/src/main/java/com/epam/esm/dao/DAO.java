package com.epam.esm.dao;

import com.epam.esm.bean.Entity;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DAO{
    protected JdbcTemplate jdbcTemplate;

    protected DAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
