package com.epam.esm.dao.callback;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;
import com.epam.esm.config.SpringConfig;
import com.epam.esm.dao.mapper.TagMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ScrollablePreparedStatementCallback implements PreparedStatementCallback<List<Certificate>> {
    private static final String TIME_ZONE = "UTC";
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final String ID_CERTIFICATE = "id_certificate";
    private static final AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(SpringConfig.class);
    private static final TagMapper tagMapper = context.getBean(TagMapper.class);


    @Override
    public List<Certificate> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
        ResultSet resultSet = ps.executeQuery();
        List<Certificate> certificates = new ArrayList<>();
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        df.setTimeZone(tz);

        while (resultSet.next()) {
            Certificate certificate = context.getBean(Certificate.class);
            certificate.setId(resultSet.getLong(ID_CERTIFICATE));
            certificate.setName(resultSet.getString("name_certificate"));
            certificate.setDescription(resultSet.getString("description"));
            certificate.setPrice(resultSet.getDouble("price"));
            certificate.setDuration(resultSet.getShort("duration"));
            certificate.setCreateDate(df.format(resultSet.getDate("create_date")));
            certificate.setLastUpdateDate(df.format(resultSet.getDate("last_update_date")));
            certificate.setTags(mapCertificateTags(resultSet));
            certificates.add(certificate);
        }
        return certificates;
    }

    private List<Tag> mapCertificateTags(ResultSet resultSet) throws SQLException {
        List<Tag> tags = new ArrayList<>();
        long tempCertificateId = resultSet.getLong(ID_CERTIFICATE);
        while (!resultSet.isAfterLast()) {
            if (resultSet.getLong(ID_CERTIFICATE) == tempCertificateId) {
                tags.add(tagMapper.mapRow(resultSet, 1));
            } else if (resultSet.getLong(ID_CERTIFICATE) != tempCertificateId) {
                resultSet.previous();
                return tags;
            }
            resultSet.next();
        }
        return tags;
    }
}
