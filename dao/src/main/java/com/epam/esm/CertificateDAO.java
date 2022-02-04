package com.epam.esm;

import com.epam.esm.entity.CertificateEntity;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CertificateDAO extends DAO {
    Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException;

    Optional<CertificateEntity> findById(long id);

    List<CertificateEntity> findByNamePart(String namePart);

    List<CertificateEntity> findByTagName(String name);

    List<CertificateEntity> findAll();

    boolean update (CertificateEntity certificate) throws ParseException;

    boolean deleteById (long id);

    boolean addTagToCertificate(long certificateId, long tagId);

    boolean clearTagsByCertificate(long certificateId);
}
