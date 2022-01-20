package com.epam.esm.dao.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.DAO;
import com.epam.esm.dao.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class TagDAO extends DAO<Tag> {
    private static final String ADD_TAG = "insert into tag (name) values (?)";
    private static final String FIND_BY_ID = "select * from tag where id = ?";
    private static final String FIND_ALL = "select * from tag";
    private static final String UPDATE_TAG = "update tag set name = ? where id = ?";
    private static final String DELETE_TAG = "delete from tag where id = ?";
    private static final TagMapper TAG_MAPPER = new TagMapper();


    public TagDAO (JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public boolean add(Tag tag) {
        return jdbcTemplate.update(ADD_TAG, tag.getName()) == 1;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_ID, new Object[]{id},TAG_MAPPER));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, TAG_MAPPER);
    }

    @Override
    public boolean update(Tag tag) {
        return jdbcTemplate.update(UPDATE_TAG, tag.getName(), tag.getId()) >= 1;
    }

    @Override
    public boolean delete(Tag tag) {
        return jdbcTemplate.update(DELETE_TAG, tag.getId()) == 1;
    }
}
