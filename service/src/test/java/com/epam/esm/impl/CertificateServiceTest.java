package com.epam.esm.impl;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.CertificateClientModelMapper;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateServiceTest {
    CertificateValidator certificateValidator;
    CertificateServiceImpl certificateService;
    CertificateEntity certificate;
    CertificateEntity addedCertificate;
    List<TagEntity> tags;

    @Mock
    CertificateDaoImpl certificateDAO;

    @Mock
    TagDaoImpl tagDAO;

    @Mock
    TagServiceImpl tagService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        certificateValidator = new CertificateValidator();
        certificateService = new CertificateServiceImpl();
        certificateService.setCertificateDAO(certificateDAO);
        certificateService.setValidator(certificateValidator);
        certificateService.setTagDAO(tagDAO);
        tagService.setTagDAO(tagDAO);
        certificateService.setTagService(tagService);
    }

    @BeforeEach
    void configuration() {
        certificate = new CertificateEntity();
        certificate.setName("a");
        certificate.setDescription("a");
        certificate.setPrice(BigDecimal.valueOf(5));
        certificate.setDuration(30);

        addedCertificate = new CertificateEntity();
        addedCertificate.setId(1);
        addedCertificate.setName("a");
        addedCertificate.setDescription("a");
        addedCertificate.setPrice(BigDecimal.valueOf(5));
        addedCertificate.setDuration(30);

        tags = Arrays.asList(new TagEntity(1, "a"), new TagEntity(2, "a"));
    }

    @Test
    void addTest() throws ParseException {
        when(certificateDAO.add(certificate))
                .thenReturn(Optional.ofNullable(addedCertificate));
        when(certificateDAO.findByNamePart(certificate.getName()))
                .thenReturn(Arrays.asList(addedCertificate));
        assertEquals(certificateService.add(certificate),
                CertificateClientModelMapper.INSTANCE.certificateToCertificateClientModel(addedCertificate));
    }

    @Test
    void addTest1ForThrowing() {
        certificate.setName("");
        assertThrows(BadNameException.class,
                () -> certificateService.add(certificate));
    }

    @Test
    void addTest2ForThrowing() {
        certificate.setPrice(new BigDecimal(0));
        assertThrows(BadPriceException.class,
                () -> certificateService.add(certificate));
    }

    @Test
    void addTest3ForThrowing() {
        certificate.setDuration(0);
        assertThrows(BadDurationException.class,
                () -> certificateService.add(certificate));
    }

    @Test
    void addTest4ForThrowing() {
        certificate.setDescription("");
        assertThrows(BadDescriptionException.class,
                () -> certificateService.add(certificate));
    }



    @Test
    void addTagToCertificateTest() {
        when(certificateDAO.addTagToCertificate(1, 1))
                .thenReturn(true);
        assertTrue(certificateService.addTagToCertificate(1, 1));
    }

    @Test
    void findAllTest() {
        addedCertificate.setTags(tags);
        when(certificateDAO.findAll())
                .thenReturn(Arrays.asList(addedCertificate));
        assertEquals(certificateService.findAll(null),
                Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void findCertificateByIdTest() {
        when(certificateDAO.findById(1))
                .thenReturn(Optional.ofNullable(addedCertificate));
        assertEquals(certificateService.findCertificateById(1),
                CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate));
    }

    @Test
    void findCertificateByIdTestForThrowing() {
        when(certificateDAO.findById(1))
                .thenReturn(Optional.empty());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.findCertificateById(1));
    }

    @Test
    void findByNameTest() {
        when(certificateDAO.findByNamePart("a"))
                .thenReturn(Arrays.asList(addedCertificate));
        assertEquals(certificateService.findByName("a", null),
                Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void findByTagNameTest() {
        addedCertificate.setTags(tags);
        when(certificateDAO.findByTagName("a"))
                .thenReturn(Arrays.asList(addedCertificate));
        assertEquals(certificateService.findByTagName("a", null),
                Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void updateTest() {
        when(certificateDAO.findByNamePart(addedCertificate.getName()))
                .thenReturn(Arrays.asList(addedCertificate));
        when(certificateDAO.update(addedCertificate))
                .thenReturn(true);
        assertEquals(certificateService.update(addedCertificate),
                CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate));
    }

    @Test
    void updateTestForThrowing() {
        when(certificateDAO.findByNamePart(addedCertificate.getName()))
                .thenReturn(new ArrayList<>());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.update(addedCertificate));
    }

    @Test
    void deleteByIdTest() {
        when(certificateDAO.findById(addedCertificate.getId()))
                .thenReturn(Optional.ofNullable(addedCertificate));
        assertTrue(certificateService.deleteById(addedCertificate.getId()));
    }

    @Test
    void deleteByIdForThrowing() {
        when(certificateDAO.findById(addedCertificate.getId()))
                .thenReturn(Optional.empty());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.deleteById(addedCertificate.getId()));
    }
}