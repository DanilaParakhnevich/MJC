package com.epam.esm.dao.extractor;

import com.epam.esm.mapper.TagMapper;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateExtractor implements ResultSetExtractor<List<CertificateEntity>> {
    private TagMapper tagMapper;

    @Override
    public List<CertificateEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<CertificateEntity> certificates = new ArrayList<>();
        while (rs.next()) {
            CertificateEntity certificate = new CertificateEntity();
            fillCertificateFields(certificate, rs);
            fillCertificateWithTags(certificate, rs);
            certificates.add(certificate);
        }
        return certificates;
    }

    void fillCertificateFields(CertificateEntity certificate, ResultSet rs) throws SQLException {
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
        certificate.setDuration(rs.getLong("duration"));
        certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
    }

    private void fillCertificateWithTags(CertificateEntity certificate, ResultSet rs) throws SQLException {
        List<TagEntity> tags = new ArrayList<>();
        long certificateId = rs.getLong("id");
        while (!rs.isAfterLast()) {
            if (rs.getLong("id") == certificateId) {
                TagEntity tag = tagMapper.mapRow(rs, 1);
                if (tag.getId() != 0) {
                    tags.add(tagMapper.mapRow(rs, 1));
                }
            } else {
                rs.previous();
                certificate.setTags(tags);
                return;
            }
            rs.next();
        }
        certificate.setTags(tags);
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }
}
