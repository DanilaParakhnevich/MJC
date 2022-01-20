package com.epam.esm.dao.mapper;


import com.epam.esm.bean.Tag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Tag tag = (Tag) context.getBean("tag");
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
