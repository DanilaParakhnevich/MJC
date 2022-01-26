package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDAOImpl extends TagDAO {
    private static final String ADD_TAG = "insert into tag (name) values (?)";
    private static final String FIND_BY_ID = "select * from tag where id = ?";
    private static final String FIND_BY_NAME = "select * from tag where name = ?";
    private static final String FIND_ALL = "select * from tag";
    private static final String DELETE_TAG = "delete from tag where id = ?";
    private static final TagMapper TAG_MAPPER = new TagMapper();


    TagDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Tag> add(Tag tag) {
        return Optional.ofNullable(jdbcTemplate.update(ADD_TAG, tag.getName()) == 1
                        ? tag : null);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_ID, new Object[]{id},TAG_MAPPER));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_NAME, new Object[]{name},TAG_MAPPER));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, TAG_MAPPER);
    }

    @Override
    public boolean delete(Tag tag) {
        return jdbcTemplate.update(DELETE_TAG, tag.getId()) == 1;
    }
}
