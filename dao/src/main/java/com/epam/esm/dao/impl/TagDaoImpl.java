package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {
    private static final String READ_ALL = "select tag.id as tag_id, tag.name as tag_name from tag order by tag.id limit ? offset ?";
    private static final String READ_BY_ID = "select tag.id as tag_id, tag.name as tag_name from tag where tag.id = ?";
    private static final String READ_BY_NAME = "select tag.id as tag_id, tag.name as tag_name from tag where tag.name = ?";
    private static final String READ_BY_CERTIFICATE_ID = "select tag.id as tag_id, tag.name as tag_name from tag join " +
            "certificate_by_tag on tag.id = certificate_by_tag.id_tag join gift_certificate " +
            "on gift_certificate.id = certificate_by_tag.id_certificate where gift_certificate.id = ?";
    private static final String CREATE = "insert into tag (name) values (?)";
    private static final String DELETE = "delete from tag where tag.id = ?";
    private JdbcTemplate jdbcTemplate;
    private TagMapper tagMapper;


    @Override
    public TagEntity create(TagEntity tag) {
        jdbcTemplate.update(CREATE, tag.getName());
        return jdbcTemplate.query(READ_BY_NAME, tagMapper, tag.getName()).get(0);
    }

    @Override
    public List<TagEntity> readAll(long page, long pageSize) {
        return jdbcTemplate.query(READ_ALL, tagMapper, pageSize, (page - 1) * pageSize);
    }

    @Override
    public List<TagEntity> readByCertificateId(long id) {
        return jdbcTemplate.query(READ_BY_CERTIFICATE_ID, tagMapper, id);
    }

    @Override
    public Optional<TagEntity> readByName(String name) {
        List<TagEntity> tags = jdbcTemplate.query(READ_BY_NAME, tagMapper, name);
        if (tags.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(tags.get(0));
    }

    @Override
    public Optional<TagEntity> readById(long id) {
        List<TagEntity> tags = jdbcTemplate.query(READ_BY_ID, tagMapper, id);
        if (!tags.isEmpty()) {
            return Optional.ofNullable(tags.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Autowired
    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }
}
