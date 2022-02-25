package com.epam.esm.mapper;


import com.epam.esm.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for TagEntity.
 * @see com.epam.esm.entity.TagEntity
 */
@Component
public class TagMapperImpl implements RowMapper<TagEntity> {
    @Override
    public TagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
