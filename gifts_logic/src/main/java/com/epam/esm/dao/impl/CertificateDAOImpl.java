package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.callback.ScrollablePreparedStatementCallback;
import com.epam.esm.dao.extractor.CertificateExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CertificateDAOImpl extends CertificateDAO {
    private static final String ADD_CERTIFICATE = "insert into gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date)" +
            " values (?, ?, ?, ?, ?, ?)";
    private static final String ADD_TAG_TO_CERTIFICATE = "insert into certificate_by_tag (id_certificate, id_tag)" +
            " values (?, ?)";
    private static final String FIND_BY_ID = "SELECT gift_certificate.id as id_certificate," +
            " gift_certificate.name as name_certificate, gift_certificate.description, gift_certificate.price," +
            " gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date, tag.id AS" +
            " id_tag, tag.name AS name_tag FROM gift_certificate LEFT JOIN certificate_by_tag" +
            " ON gift_certificate.id = certificate_by_tag.id_certificate LEFT JOIN tag ON certificate_by_tag.id_tag = tag.id" +
            " WHERE gift_certificate.id = ?";
    private static final String FIND_BY_NAME = "SELECT gift_certificate.id as id_certificate," +
            " gift_certificate.name as name_certificate, gift_certificate.description, gift_certificate.price," +
            " gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date, tag.id AS" +
            " id_tag, tag.name AS name_tag FROM gift_certificate LEFT JOIN certificate_by_tag" +
            " ON gift_certificate.id = certificate_by_tag.id_certificate LEFT JOIN tag ON certificate_by_tag.id_tag = tag.id" +
            " WHERE gift_certificate.name LIKE CONCAT('%', ?, '%')";
    private static final String FIND_BY_TAG = "SELECT gift_certificate.id AS id_certificate," +
            " gift_certificate.name AS name_certificate, gift_certificate.description, gift_certificate.price," +
            " gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date, tag.id AS" +
            " tag_id, tag.name AS tag_name FROM gift_certificate LEFT JOIN certificate_by_tag" +
            " ON gift_certificate.id = certificate_by_tag.id_certificate LEFT JOIN tag ON certificate_by_tag.id_tag = tag.id" +
            " WHERE tag.name = ?";
    private static final String FIND_ALL = "SELECT gift_certificate.id as id_certificate," +
            " gift_certificate.name as name_certificate, gift_certificate.description, gift_certificate.price," +
            " gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date, tag.id AS" +
            " id, tag.name AS name FROM gift_certificate LEFT JOIN certificate_by_tag" +
            " ON gift_certificate.id = certificate_by_tag.id_certificate LEFT JOIN tag ON certificate_by_tag.id_tag = tag.id" +
            " ORDER BY gift_certificate.id";
    private static final String UPDATE_CERTIFICATE = "update certificate set name = ?, description = ?, " +
            "price = ?, duration = ?, create_date = ?, last_update_date = ? where id = ?";
    private static final String DELETE_TAG = "delete from certificate where id = ?";
    private static final String DELETE_TAGS_BY_CERTIFICATE = "delete from certificate_by_tag where certificate_id = ?";
    private static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm";
    private static final String DATA_PATTERN = "\\+0([0-9]){1}\\:00";
    private static final long DAY_IN_MILLISECONDS = 86400000;
    private static final SimpleDateFormat ISO_8601_DATE_FORMAT
            = new SimpleDateFormat(ISO8601_PATTERN, Locale.getDefault());
    private static final CertificateExtractor certificateExtractor = new CertificateExtractor();
    private static final ScrollablePreparedStatementCallback statementCallback = new ScrollablePreparedStatementCallback();



    CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Certificate> add(Certificate certificate) throws ParseException {
        return Optional.ofNullable(jdbcTemplate.update(ADD_CERTIFICATE,
                certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(),
                convertISO8601ToDate(certificate.getCreateDate()),
                convertISO8601ToDate(certificate.getLastUpdateDate()))
                == 1 ? certificate : null);
    }

    @Override
    public Optional<Certificate> findById(long id) {
        return Optional.ofNullable(jdbcTemplate
                .query(FIND_BY_ID, certificateExtractor, id).get(0));
    }

    @Override
    public List<Certificate> findByNamePart(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, certificateExtractor, name);
    }

    @Override
    public List<Certificate> findByTagName(String tag) {
        return jdbcTemplate.query(FIND_BY_TAG, certificateExtractor, tag);

    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.execute(FIND_ALL, statementCallback);
    }

    @Override
    public boolean update(Certificate certificate) throws ParseException {
        return jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), convertISO8601ToDate(certificate.getCreateDate()),
                convertISO8601ToDate(certificate.getLastUpdateDate()), certificate.getId()) >= 1;
    }

    @Override
    public boolean delete(Certificate certificate) {
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
}
