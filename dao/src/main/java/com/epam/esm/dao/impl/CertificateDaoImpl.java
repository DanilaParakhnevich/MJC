package com.epam.esm.dao.impl;

import com.epam.esm.dao.extractor.CertificateExtractor;
import com.epam.esm.dao.extractor.CertificateExtractorForPagination;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.dao.CertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CertificateDaoImpl implements CertificateDao {
    private static final String CREATE_CERTIFICATE = "insert into gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) " +
            "values (?,?,?,?,?,?)";
    private static final String READ_ALL = "select c.id as id, c.name as name, c.description as description, c.price as price, " +
            "c.duration as duration, c.create_date as create_date, c.last_update_date as last_update_date from gift_certificate c " +
            "order by c.id asc limit ? offset ?";
    private static final String READ_BY_ID = "select c.id as id, c.name as name, c.description as description, c.price as price, " +
            "c.duration as duration, c.create_date as create_date, c.last_update_date as last_update_date, t.id as tag_id, " +
            "t.name as tag_name from gift_certificate c left join certificate_by_tag " +
            "on c.id = certificate_by_tag.id_certificate left join tag t on certificate_by_tag.id_tag = t.id " +
            "where c.id = ?";
    private static final String READ_BY_NAME = "select c.id as id, c.name as name, c.description as description, c.price as price, " +
            "c.duration as duration, c.create_date as create_date, c.last_update_date as last_update_date, t.id as tag_id, " +
            "t.name as tag_name from gift_certificate c left join certificate_by_tag " +
            "on c.id = certificate_by_tag.id_certificate left join tag t on certificate_by_tag.id_tag = t.id " +
            "where c.name = ?";
    private static final String READ_BY_NAME_CONTAINING = "select c.id as id, c.name as name, c.description as description, c.price as price, " +
            "c.duration as duration, c.create_date as create_date, c.last_update_date as last_update_date from gift_certificate c " +
            "where lower(c.name) like lower(concat('%', concat(?, '%'))) order by c.id asc limit ? offset ?";
    private static final String READ_BY_TAG_NAME = "select c.id as id, c.name as name, c.description as description, c.price as price, " +
            "c.duration as duration, c.create_date as create_date, c.last_update_date as last_update_date from gift_certificate c " +
            "join certificate_by_tag on c.id = certificate_by_tag.id_certificate join tag on tag.id = certificate_by_tag.id_tag " +
            "where tag.name = ? order by c.id asc limit ? offset ?";
    private static final String DELETE_LINK_FROM_TAG = "delete from certificate_by_tag c where" +
            " c.id_tag = ? and c.id_certificate = ?";
    private static final String UPDATE_CERTIFICATE = "update gift_certificate " +
            "set name = ?, description = ?, price = ?, duration = ?, create_date = ?, last_update_date = ? " +
            "where gift_certificate.id = ?";
    private static final String CREATE_LINK_WITH_TAG = "insert into certificate_by_tag " +
            "(id_tag, id_certificate) values(?, ?)";
    private static final String DELETE_BY_ID = "delete from gift_certificate where gift_certificate.id = ?";
    private JdbcTemplate jdbcTemplate;
    private CertificateExtractor certificateExtractor;
    private CertificateExtractorForPagination certificateExtractorForPagination;


    @Override
    public List<CertificateEntity> findAllByNameContainingIgnoreCase(String name, long page, long pageSize) {
        return jdbcTemplate.query(READ_BY_NAME_CONTAINING, certificateExtractorForPagination, name, pageSize, (page - 1) * pageSize);
    }

    @Override
    public Optional<CertificateEntity> findByName(String name) {
        List<CertificateEntity> certificates = jdbcTemplate.query(READ_BY_NAME,
                certificateExtractor, name);
        if (certificates == null || certificates.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(certificates.get(0));
    }

    @Override
    public List<CertificateEntity> findAllByTags(String tagName, long page, long pageSize) {
        return jdbcTemplate.query(READ_BY_TAG_NAME, certificateExtractorForPagination,
                tagName, pageSize, (page - 1) * pageSize);
    }

    @Override
    public List<CertificateEntity> findAll(long page, long pageSize) {
        return jdbcTemplate.query(READ_ALL, certificateExtractorForPagination,
                pageSize, (page - 1) * pageSize );
    }

    @Override
    public Optional<CertificateEntity> findById(long id) {
        List<CertificateEntity> certificates = jdbcTemplate.query(READ_BY_ID,
                certificateExtractor, id);
        if (certificates == null || certificates.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(certificates.get(0));
    }


    @Override
    public void create(CertificateEntity certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(certificate.getCreateDate());
        jdbcTemplate.update(CREATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate());
    }

    @Override
    public void update(CertificateEntity certificate) {
        certificate.setLastUpdateDate(LocalDateTime.now());
        jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), certificate.getId());
    }

    @Override
    public void delete(CertificateEntity certificateEntity) {
        jdbcTemplate.update(DELETE_BY_ID, certificateEntity.getId());
    }

    @Override
    public void createLink(long tagId, long certificateId) {
        jdbcTemplate.update(CREATE_LINK_WITH_TAG, tagId, certificateId);
    }

    @Override
    public void deleteLink(long tagId, long certificateId) {
        jdbcTemplate.update(DELETE_LINK_FROM_TAG, tagId, certificateId);
    }

    @Autowired
    public void setCertificateExtractor(CertificateExtractor certificateExtractor) {
        this.certificateExtractor = certificateExtractor;
    }

    @Autowired
    public void setCertificateExtractorForPagination(CertificateExtractorForPagination certificateExtractorForPagination) {
        this.certificateExtractorForPagination = certificateExtractorForPagination;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
