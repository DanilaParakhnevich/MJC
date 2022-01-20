package com.epam.esm.dao;

import com.epam.esm.bean.Entity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class DAO <T extends Entity>{
    protected JdbcTemplate jdbcTemplate;

    protected DAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public abstract boolean add(T entity);

    public abstract Optional<T> findById(long id);

    public abstract List<T> findAll();

    public abstract boolean update (T entity);

    public abstract boolean delete (T entity);
}
