package com.epam.esm.mapper;

import com.epam.esm.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<TagEntity> {
    @Override
    public TagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setId(rs.getLong("tag_id"));
        tag.setName(rs.getString("tag_name"));
        return tag;
    }
}
