package com.epam.esm.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.dao.CertificateDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateRepositoryTest {
    private CertificateDao certificateRepository;
    private CertificateEntity testCertificate;
    private CertificateEntity firstTestCertificate;
    private CertificateEntity secondTestCertificate;

    @BeforeEach
    void setUp() {
        testCertificate = new CertificateEntity(4, "jumping", "jumping",
                new BigDecimal("10.0"), 10, LocalDateTime.parse("2022-03-15T03:00"),
                LocalDateTime.parse("2022-03-15T03:00"), null);
        firstTestCertificate = new CertificateEntity(2, "swimming", "swimming",
                new BigDecimal("33.0"), 35, LocalDateTime.parse("2022-03-15T03:00"),
                LocalDateTime.parse("2022-03-15T03:00"), null);
        secondTestCertificate = new CertificateEntity(3, "basketball", "basketball",
                new BigDecimal("23.0"), 35, LocalDateTime.parse("2022-03-15T03:00"),
                LocalDateTime.parse("2022-03-15T03:00"), null);
    }


    @Test
    void add() throws ParseException {
        CertificateEntity certificate = certificateRepository.create(testCertificate);
        testCertificate.setLastUpdateDate(certificate.getLastUpdateDate());
        testCertificate.setCreateDate(certificate.getCreateDate());
        assertEquals(certificate, testCertificate);
    }

    @Test
    void findById() {
        testCertificate.setId(1);
        assertEquals(testCertificate, certificateRepository.findById(1L).get());
    }

    @Test
    void findByNamePart() {
        assertEquals(firstTestCertificate, certificateRepository.findAllByNameContainingIgnoreCase("swimming").get(0));
    }

    @Test
    void findByTagName() {
        assertEquals(firstTestCertificate, certificateRepository.findAllByTag("clear").get(0));
    }

    @Test
    void findAll() {
        testCertificate.setId(1);
        assertEquals(certificateRepository.findAll(), Arrays.asList(testCertificate, firstTestCertificate, secondTestCertificate));
    }

    @Test
    void update() {
        assertTrue(certificateRepository.create(firstTestCertificate) == firstTestCertificate);
    }

    @Test
    void deleteById() {
        certificateRepository.deleteById(1L);
        assertEquals(certificateRepository.findAll(), Arrays.asList(firstTestCertificate, secondTestCertificate));
    }

    @Autowired
    public void setCertificateRepository(CertificateDao certificateRepository) {
        this.certificateRepository = certificateRepository;
    }
}