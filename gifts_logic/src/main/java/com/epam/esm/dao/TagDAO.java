package com.epam.esm.dao;

import com.epam.esm.bean.Tag;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class TagDAO extends DAO{
    protected TagDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public abstract Optional<Tag> add(Tag tag);

    public abstract Optional<Tag> findById(long id);

    public abstract Optional<Tag> findByName(String name);

    public abstract List<Tag> findAll();

    public abstract boolean delete (Tag tag);
}
