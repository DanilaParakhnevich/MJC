package com.epam.esm.dao.mapper;


import com.epam.esm.bean.Certificate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateMapper implements RowMapper<Certificate> {

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Certificate certificate = (Certificate) context.getBean("certificate");
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setPrice(rs.getDouble("price"));
        certificate.setDescription(rs.getString("description"));
        certificate.setDuration(rs.getLong("duration"));
        certificate.setCreateDate(rs.getString("create_date"));
        certificate.setLastUpdateDate(rs.getString("last_update_date"));
        return certificate;
    }
}
