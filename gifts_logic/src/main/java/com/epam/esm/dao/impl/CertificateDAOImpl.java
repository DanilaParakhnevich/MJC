package com.epam.esm.dao.impl;

import com.epam.esm.bean.Certificate;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.mapper.CertificateMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class CertificateDAOImpl extends CertificateDAO {
    private static final String ADD_CERTIFICATE = "insert into certificate " +
            "(name, description, price, duration, create_date, last_update_date)" +
            " values (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "select * from certificate where id = ?";
    private static final String FIND_ALL = "select * from certificate";
    private static final String UPDATE_CERTIFICATE = "update certificate set name = ?, description = ?, " +
            "price = ?, duration = ?, create_date = ?, last_update_date = ? where id = ?";
    private static final String DELETE_TAG = "delete from certificate where id = ?";
    private static final CertificateMapper CERTIFICATE_MAPPER = new CertificateMapper();

    protected CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Certificate> add(Certificate certificate) {
        return Optional.ofNullable(jdbcTemplate.update(ADD_CERTIFICATE,
                certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(),
                certificate.getCreateDate(), certificate.getLastUpdateDate())
                == 1 ? certificate : null);
    }

    @Override
    public Optional<Certificate> findById(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(FIND_BY_ID, new Object[]{id},CERTIFICATE_MAPPER));
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(FIND_ALL, CERTIFICATE_MAPPER);
    }

    @Override
    public boolean update(Certificate certificate) {
        return jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), certificate.getId()) >= 1;
    }

    @Override
    public boolean delete(Certificate certificate) {
        return jdbcTemplate.update(DELETE_TAG, certificate.getId()) == 1;
    }
}
