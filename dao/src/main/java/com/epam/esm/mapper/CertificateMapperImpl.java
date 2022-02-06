package com.epam.esm.mapper;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Mapper for CertificateEntity.
 * @see com.epam.esm.entity.CertificateEntity
 */
@Component
@Scope("singleton")
public class CertificateMapperImpl implements RowMapper<CertificateEntity> {
    @Override
    public CertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CertificateEntity certificate = new CertificateEntity();
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
        certificate.setDuration(rs.getLong("duration"));
        certificate.setCreateDate(dateToLocalDateTime(rs.getDate("create_date")));
        certificate.setLastUpdateDate(dateToLocalDateTime(rs.getDate("last_update_date")));
        return certificate;
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}

