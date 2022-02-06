package com.epam.esm.mapper;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

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
        certificate.setCreateDate(rs.getTimestamp("create_date")
                .toLocalDateTime()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date")
                .toLocalDateTime()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        return certificate;
    }

}

