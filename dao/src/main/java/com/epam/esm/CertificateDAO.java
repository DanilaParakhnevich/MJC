package com.epam.esm;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public abstract class CertificateDAO extends DAO {
    protected CertificateDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public abstract Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException;

    public abstract Optional<CertificateEntity> findById(long id);

    public abstract List<CertificateEntity> findByNamePart(String namePart);

    public abstract List<CertificateEntity> findAll();

    public abstract boolean update (CertificateEntity certificate) throws ParseException;

    public abstract boolean delete (CertificateEntity certificate);

    public abstract boolean addTagToCertificate(long certificateId, long tagId);

    public abstract boolean clearTagsByCertificate(long certificateId);
}
