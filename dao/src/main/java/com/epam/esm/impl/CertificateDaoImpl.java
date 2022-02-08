package com.epam.esm.impl;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.CertificateDao;
import com.epam.esm.mapper.CertificateMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DAO for CertificateEntity.
 * @see com.epam.esm.entity.CertificateEntity
 */
@Component
public class CertificateDaoImpl extends CertificateDao {
    private static final String ADD_CERTIFICATE = "insert into gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date)" +
            " values (?, ?, ?, ?, ?, ?)";
    private static final String ADD_TAG_TO_CERTIFICATE = "insert into certificate_by_tag (id_certificate, id_tag)" +
            " values (?, ?)";
    private static final String FIND_BY_NAME = "select * from gift_certificate where name like CONCAT('%', ?, '%')";
    private static final String FIND_BY_TAG = "select * from gift_certificate" +
            " join certificate_by_tag on gift_certificate.id = certificate_by_tag.id_certificate" +
            " join tag on certificate_by_tag.id_tag = tag.id where tag.name = ?";
    private static final String UPDATE_CERTIFICATE = "update gift_certificate set name = ?, description = ?, " +
            "price = ?, duration = ?, create_date = ?, last_update_date = ? where id = ?";
    private static final String DELETE_TAGS_BY_CERTIFICATE = "delete from certificate_by_tag" +
            " where id_certificate = ?";
    private static final String FIND_LAST_ADDED_CERTIFICATE = "select * from gift_certificate" +
            " where id = (select MAX(id) from gift_certificate)";

    public CertificateDaoImpl() {
        setTableName("gift_certificate");
    }

    @Override
    public Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return Optional.ofNullable(jdbcTemplate.update(ADD_CERTIFICATE,
                certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(),
                certificate.getCreateDate(), certificate.getLastUpdateDate())
                == 1 ? findLastCertificate() : null);
    }




    @Override
    public List<CertificateEntity> findByNamePart(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, mapper, name);
    }

    @Override
    public List<CertificateEntity> findByTagName(String name) {
        return jdbcTemplate.query(FIND_BY_TAG, mapper, name);
    }

    @Override
    public boolean update(CertificateEntity certificate) {
        certificate.setLastUpdateDate(LocalDateTime.now());
        return jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), certificate.getId()) >= 1;
    }

    @Override
    public boolean addTagToCertificate(long certificateId, long tagId) {
        return jdbcTemplate.update(ADD_TAG_TO_CERTIFICATE, certificateId, tagId) == 1;
    }

    @Override
    public boolean clearTagsByCertificate(long certificateId) {
        return jdbcTemplate.update(DELETE_TAGS_BY_CERTIFICATE, certificateId) >= 1;
    }

    private CertificateEntity findLastCertificate() {
        return jdbcTemplate.queryForObject(FIND_LAST_ADDED_CERTIFICATE, mapper);
    }

    /**
     * Sets mapper.
     *
     * @param mapper the mapper
     */
    @Autowired
    public void setMapper(CertificateMapperImpl mapper) {
        this.mapper = mapper;
    }
}
