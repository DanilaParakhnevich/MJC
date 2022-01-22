package com.epam.esm.test_invoker;

import com.epam.esm.bean.Tag;
import com.epam.esm.config.SpringConfig;
import com.epam.esm.dao.impl.TagDAOImpl;

public class Main {
    public static void main(String[] args) {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("xixixi");
        SpringConfig config = new SpringConfig();
        System.out.println(new TagDAOImpl(config.jdbcTemplate(config.dataSource())).delete(tag));
    }
}
