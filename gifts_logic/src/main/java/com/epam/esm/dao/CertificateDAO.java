package com.epam.esm.dao;

import com.epam.esm.bean.Certificate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class CertificateDAO extends DAO{
    protected CertificateDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public abstract Optional<Certificate> add(Certificate certificate);

    public abstract Optional<Certificate> findById(long id);

    public abstract List<Certificate> findAll();

    public abstract boolean update (Certificate certificate);

    public abstract boolean delete (Certificate certificate);
}
