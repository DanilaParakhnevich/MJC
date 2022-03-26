package com.epam.esm.dao;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {
    List<CertificateEntity> findAllByNameContainingIgnoreCase(String name, long page, long pageSize);

    Optional<CertificateEntity> findByName(String name);

    List<CertificateEntity> findAllByTags(String tagName, long page, long pageSize);

    List<CertificateEntity> findAll(long page, long pageSize);

    Optional<CertificateEntity> findById(@Param("id") long id);

    void create(CertificateEntity certificate);

    void update(CertificateEntity certificate);

    void delete(CertificateEntity certificate);

    void createLink(long certificateId, long tagId);

    void deleteLink(long certificateId, long tagId);
}
