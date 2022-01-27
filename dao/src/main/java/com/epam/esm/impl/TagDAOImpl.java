package com.epam.esm.impl;

import com.epam.esm.TagDAO;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Scope("singleton")
public class TagDAOImpl extends TagDAO {
    private static final String ADD_TAG = "insert into tag (name) values (?)";
    private static final String FIND_BY_ID = "select * from tag where id = ?";
    private static final String FIND_BY_NAME = "select * from tag where name = ?";
    private static final String FIND_ALL = "select * from tag";
    private static final String DELETE_TAG = "delete from tag where id = ?";

    @Autowired
    private TagMapperImpl mapper;

    TagDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<TagEntity> add(TagEntity tag) {
        return Optional.ofNullable(jdbcTemplate.update(ADD_TAG, tag.getName()) == 1
                        ? tag : null);
    }

    @Override
    public Optional<TagEntity> findById(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_ID, new Object[]{id}, mapper));
    }

    @Override
    public Optional<TagEntity> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_NAME, new Object[]{name}, mapper));
    }

    @Override
    public List<TagEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    @Override
    public boolean delete(TagEntity tag) {
        return jdbcTemplate.update(DELETE_TAG, tag.getId()) == 1;
    }

    public void setMapper(TagMapperImpl mapper) {
        this.mapper = mapper;
    }
}
