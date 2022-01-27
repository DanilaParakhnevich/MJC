package com.epam.esm.mapper;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
@Scope("singleton")
public class CertificateMapperImpl implements RowMapper<CertificateEntity> {
    private static final String TIME_ZONE = "UTC";
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";

    @Override
    public CertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        df.setTimeZone(tz);

        CertificateEntity certificate = new CertificateEntity();
        certificate.setId(rs.getLong("id"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getDouble("price"));
        certificate.setDuration(rs.getLong("duration"));
        certificate.setCreateDate(df.format(rs.getString("create_date")));
        certificate.setLastUpdateDate(df.format(rs.getString("last_update_date")));
        return certificate;
    }
}
