package com.epam.esm.repository;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {
    public List<CertificateEntity> findByNameContainingIgnoreCase(String name);
}
