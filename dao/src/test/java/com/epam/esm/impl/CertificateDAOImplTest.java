package com.epam.esm.impl;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
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
class CertificateDAOImplTest {
    @Autowired
    CertificateDaoImpl certificateDAO;
    private CertificateEntity testCertificate;
    private CertificateEntity firstTestCertificate;
    private CertificateEntity secondTestCertificate;

    @BeforeEach
    void setUp() {
        TagEntity firstTestTag = new TagEntity(0, "Jumps");
        TagEntity secondTestTag = new TagEntity(0, "Entertainment");

        testCertificate = new CertificateEntity(4, "jumping", "jumping",
                new BigDecimal("10.0"), 10, LocalDateTime.parse("2022-03-15T00:00"),
                LocalDateTime.parse("2022-03-15T00:00"), null);
        firstTestCertificate = new CertificateEntity(2, "swimming", "swimming",
                new BigDecimal("33.0"), 35, LocalDateTime.parse("2022-03-15T00:00"),
                LocalDateTime.parse("2022-03-15T00:00"), null);
        secondTestCertificate = new CertificateEntity(3, "basketball", "basketball",
                new BigDecimal("23.0"), 35, LocalDateTime.parse("2022-03-15T00:00"),
                LocalDateTime.parse("2022-03-15T00:00"), null);
    }


    @Test
    void add() throws ParseException {
        CertificateEntity certificate = certificateDAO.add(testCertificate).get();
        testCertificate.setLastUpdateDate(certificate.getLastUpdateDate());
        testCertificate.setCreateDate(certificate.getCreateDate());
        assertEquals(certificate, testCertificate);
    }

    @Test
    void findById() {
        testCertificate.setId(1);
        assertEquals(testCertificate, certificateDAO.findById(1).get());
    }

    @Test
    void findByNamePart() {
        assertEquals(firstTestCertificate, certificateDAO.findByNamePart("swimming").get(0));
    }

    @Test
    void findByTagName() {
        testCertificate.setId(1);
        assertEquals(testCertificate, certificateDAO.findByTagName("fun").get(0));
    }

    @Test
    void findAll() {
        testCertificate.setId(1);
        assertEquals(certificateDAO.findAll(), Arrays.asList(testCertificate, firstTestCertificate, secondTestCertificate));
    }

    @Test
    void update() {
        assertTrue(certificateDAO.update(firstTestCertificate));
    }

    @Test
    void deleteById() {
        certificateDAO.deleteById(1);
        assertEquals(certificateDAO.findAll(), Arrays.asList(firstTestCertificate, secondTestCertificate));
    }

    @Test
    void addTagToCertificate() {
        assertTrue(certificateDAO.addTagToCertificate(1, 1));
    }
}