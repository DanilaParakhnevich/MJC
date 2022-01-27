package com.epam.esm;

import com.epam.esm.entity.TagEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class TagDAO extends DAO {
    protected TagDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public abstract Optional<TagEntity> add(TagEntity tag);

    public abstract Optional<TagEntity> findById(long id);

    public abstract Optional<TagEntity> findByName(String name);

    public abstract List<TagEntity> findAll();

    public abstract boolean delete (TagEntity tag);
}
