package com.epam.esm.impl;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.CertificateClientModelMapper;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateServiceTest {
    CertificateValidator certificateValidator;
    CertificateServiceImpl certificateService;
    CertificateEntity certificate;
    CertificateEntity addedCertificate;
    List<TagEntity> tags;

    @Mock
    CertificateDAOImpl certificateDAO;

    @Mock
    TagDAOImpl tagDAO;

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
        certificate.setPrice(5);
        certificate.setDuration(30);

        addedCertificate = new CertificateEntity();
        addedCertificate.setId(1);
        addedCertificate.setName("a");
        addedCertificate.setDescription("a");
        addedCertificate.setPrice(5);
        addedCertificate.setDuration(30);

        tags = Arrays.asList(new TagEntity(1, "a"), new TagEntity(2, "a"));
    }

    @Test
    void addTest() throws ParseException {
        Mockito.when(certificateDAO.add(certificate))
                .thenReturn(Optional.ofNullable(addedCertificate));
        Mockito.when(certificateDAO.findByNamePart(certificate.getName()))
                .thenReturn(Arrays.asList(addedCertificate));
        Assertions.assertEquals(certificateService.add(certificate),
                CertificateClientModelMapper.INSTANCE.certificateToCertificateClientModel(addedCertificate));
    }

    @Test
    void addTest1ForThrowing() {
        certificate.setName("");
        Assertions.assertThrows(BadNameException.class,
                () -> certificateService.add(certificate));
    }

    @Test
    void addTest2ForThrowing() {
        certificate.setPrice(0);
        Assertions.assertThrows(BadPriceException.class,
                () -> certificateService.add(certificate));
    }

    @Test
    void addTest3ForThrowing() {
        certificate.setDuration(0);
        Assertions.assertThrows(BadDurationException.class,
                () -> certificateService.add(certificate));
    }

    @Test
    void addTest4ForThrowing() {
        certificate.setDescription("");
        Assertions.assertThrows(BadDescriptionException.class,
                () -> certificateService.add(certificate));
    }



    @Test
    void addTagToCertificateTest() {
        Mockito.when(certificateDAO.addTagToCertificate(1, 1))
                .thenReturn(true);
        Assertions.assertTrue(certificateService.addTagToCertificate(1, 1));
    }

    @Test
    void findAllTest() {
        addedCertificate.setTags(tags);
        Mockito.when(certificateDAO.findAll())
                .thenReturn(Arrays.asList(addedCertificate));
        Assertions.assertEquals(certificateService.findAll(null),
                Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void findCertificateByIdTest() {
        Mockito.when(certificateDAO.findById(1))
                .thenReturn(Optional.ofNullable(addedCertificate));
        Assertions.assertEquals(certificateService.findCertificateById(1),
                CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate));
    }

    @Test
    void findCertificateByIdTestForThrowing() {
        Mockito.when(certificateDAO.findById(1))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UnknownCertificateException.class,
                () -> certificateService.findCertificateById(1));
    }

    @Test
    void findByNameTest() {
        Mockito.when(certificateDAO.findByNamePart("a"))
                .thenReturn(Arrays.asList(addedCertificate));
        Assertions.assertEquals(certificateService.findByName("a", null),
                Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void findByTagNameTest() {
        addedCertificate.setTags(tags);
        Mockito.when(certificateDAO.findByTagName("a"))
                .thenReturn(Arrays.asList(addedCertificate));
        Assertions.assertEquals(certificateService.findByTagName("a", null),
                Arrays.asList(CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void updateTest() {
        Mockito.when(certificateDAO.findByNamePart(addedCertificate.getName()))
                .thenReturn(Arrays.asList(addedCertificate));
        Mockito.when(certificateDAO.update(addedCertificate))
                .thenReturn(true);
        Assertions.assertEquals(certificateService.update(addedCertificate),
                CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(addedCertificate));
    }

    @Test
    void updateTestForThrowing() {
        Mockito.when(certificateDAO.findByNamePart(addedCertificate.getName()))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(UnknownCertificateException.class,
                () -> certificateService.update(addedCertificate));
    }

    @Test
    void deleteByIdTest() {
        Mockito.when(certificateDAO.findById(addedCertificate.getId()))
                .thenReturn(Optional.ofNullable(addedCertificate));
        Assertions.assertTrue(certificateService.deleteById(addedCertificate.getId()));
    }

    @Test
    void deleteByIdForThrowing() {
        Mockito.when(certificateDAO.findById(addedCertificate.getId()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UnknownCertificateException.class,
                () -> certificateService.deleteById(addedCertificate.getId()));
    }
}