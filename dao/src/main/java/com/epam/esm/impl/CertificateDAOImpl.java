package com.epam.esm.impl;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.CertificateDAO;
import com.epam.esm.mapper.CertificateMapperImpl;
import com.epam.esm.mapper.TagMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Scope("singleton")
public class CertificateDAOImpl extends CertificateDAO {
    private static final String ADD_CERTIFICATE = "insert into gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date)" +
            " values (?, ?, ?, ?, ?, ?)";
    private static final String ADD_TAG_TO_CERTIFICATE = "insert into certificate_by_tag (id_certificate, id_tag)" +
            " values (?, ?)";
    private static final String FIND_BY_ID = "select * from certificate where id = ?";
    private static final String FIND_BY_NAME = "select * from certificate where name like CONCAT('%', ?, '%')";
    private static final String FIND_ALL = "select * from certificate";
    private static final String UPDATE_CERTIFICATE = "update certificate set name = ?, description = ?, " +
            "price = ?, duration = ?, create_date = ?, last_update_date = ? where id = ?";
    private static final String DELETE_TAG = "delete from certificate where id = ?";
    private static final String DELETE_TAGS_BY_CERTIFICATE = "delete from certificate_by_tag where certificate_id = ?";
    private static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm";
    private static final String DATA_PATTERN = "\\+0([0-9]){1}\\:00";
    private static final long DAY_IN_MILLISECONDS = 86400000;
    private static final SimpleDateFormat ISO_8601_DATE_FORMAT
            = new SimpleDateFormat(ISO8601_PATTERN, Locale.getDefault());
    @Autowired()
    private CertificateMapperImpl mapper;


    CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException {
        return Optional.ofNullable(jdbcTemplate.update(ADD_CERTIFICATE,
                certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(),
                convertISO8601ToDate(certificate.getCreateDate()),
                convertISO8601ToDate(certificate.getLastUpdateDate()))
                == 1 ? certificate : null);
    }


    @Override
    public Optional<CertificateEntity> findById(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_ID, mapper, id));
    }

    @Override
    public List<CertificateEntity> findByNamePart(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, mapper, name);
    }

    @Override
    public List<CertificateEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    @Override
    public boolean update(CertificateEntity certificate) throws ParseException {
        return jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), convertISO8601ToDate(certificate.getCreateDate()),
                convertISO8601ToDate(certificate.getLastUpdateDate()), certificate.getId()) >= 1;
    }

    @Override
    public boolean delete(CertificateEntity certificate) {
        return jdbcTemplate.update(DELETE_TAG, certificate.getId()) == 1;
    }

    @Override
    public boolean addTagToCertificate(long certificateId, long tagId) {
        return jdbcTemplate.update(ADD_TAG_TO_CERTIFICATE, certificateId, tagId) == 1;
    }

    @Override
    public boolean clearTagsByCertificate(long certificateId) {
        return jdbcTemplate.update(DELETE_TAGS_BY_CERTIFICATE, certificateId) >= 1;
    }

    private Date convertISO8601ToDate(String iso8601Date) throws ParseException {
        String date = iso8601Date.replaceAll(DATA_PATTERN, "+0$100");
        Date parsedDate = ISO_8601_DATE_FORMAT.parse(date);
        parsedDate.setTime(parsedDate.getTime() + DAY_IN_MILLISECONDS);
        return parsedDate;
    }

    public void setMapper(CertificateMapperImpl mapper) {
        this.mapper = mapper;
    }
}
