package com.epam.esm.impl;

import com.epam.esm.Dao;
import com.epam.esm.TagDao;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * DAO for TagEntity.
 * @see com.epam.esm.entity.TagEntity
 */
@Component
public class TagDaoImpl extends Dao<TagEntity> implements TagDao {
    private static final String ADD_TAG = "insert into tag (name) values (?)";
    private static final String FIND_BY_CERTIFICATE_ID = "select tag.id, tag.name from tag" +
            " right join certificate_by_tag on certificate_by_tag.id_tag = tag.id" +
            " where certificate_by_tag.id_certificate = ?";
    private static final String FIND_BY_NAME = "select * from tag where name = ?";

    public TagDaoImpl() {
        setTableName("tag");
    }

    @Override
    public Optional<TagEntity> add(TagEntity tag) {
        return Optional.ofNullable(jdbcTemplate.update(ADD_TAG, tag.getName()) == 1
                        ? findByName(tag.getName()).get() : null);
    }

    @Override
    public List<TagEntity> findByCertificateId(long id) {
        return jdbcTemplate.query(FIND_BY_CERTIFICATE_ID, mapper, id);
    }

    @Override
    public Optional<TagEntity> findByName(String name) {
        List<TagEntity> tags = jdbcTemplate
                .query(FIND_BY_NAME, mapper, name);
        return tags.isEmpty() ? Optional.empty() : Optional.ofNullable(tags.get(0));
    }

    /**
     * Sets mapper.
     *
     * @param mapper the mapper
     */
    @Autowired
    public void setMapper(TagMapperImpl mapper) {
        this.mapper = mapper;
    }
}

