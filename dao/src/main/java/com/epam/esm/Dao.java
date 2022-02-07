package com.epam.esm;


import com.epam.esm.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

/**
 * The interface Dao.
 */
public abstract class Dao<T extends Entity> {
    private String findAll = "select * from &";
    private String findById = "select * from & where id = ?";
    private String delete = "delete from & where id = ?";
    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> mapper;

    public List<T> findAll() {
        return jdbcTemplate.query(findAll, mapper);
    }

    public Optional<T> findById(long id) {
        List<T> tList = jdbcTemplate
                .query(findById, mapper, id);
        return tList.isEmpty() ? Optional.empty()
                : Optional.ofNullable(tList.get(0));
    }

    public boolean delete(long id) {
        return jdbcTemplate.update(delete, id) == 1;
    }


    protected final void setTableName(String tableName) {
        findAll = replaceTableNameIntoScript(findAll, tableName);
        findById = replaceTableNameIntoScript(findById, tableName);
        delete = replaceTableNameIntoScript(delete, tableName);
    }

    private String replaceTableNameIntoScript (String script, String tableName) {
        return script.replace("&", tableName);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
