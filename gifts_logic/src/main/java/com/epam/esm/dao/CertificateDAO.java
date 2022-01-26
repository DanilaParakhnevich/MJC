package com.epam.esm.dao;

import com.epam.esm.bean.Certificate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public abstract class CertificateDAO extends DAO{
    protected CertificateDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public abstract Optional<Certificate> add(Certificate certificate) throws ParseException;

    public abstract Optional<Certificate> findById(long id);

    public abstract List<Certificate> findByNamePart(String namePart);

    public abstract List<Certificate> findByTagName(String tagName);

    public abstract List<Certificate> findAll();

    public abstract boolean update (Certificate certificate) throws ParseException;

    public abstract boolean delete (Certificate certificate);

    public abstract boolean addTagToCertificate(long certificateId, long tagId);

    public abstract boolean clearTagsByCertificate(long certificateId);
}
