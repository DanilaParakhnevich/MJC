package com.epam.esm.impl;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.handler.CertificateHandler;
import com.epam.esm.mapper.CertificateModelMapper;
import com.epam.esm.mapper.CertificateModelMapperImpl;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
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
    CertificateClientModel certificateClientModel;
    CertificateHandler handler;
    List<TagEntity> tags;
    CertificateModelMapper mapper;
    @Mock
    CertificateDaoImpl certificateDAO;

    @Mock
    TagDaoImpl tagDAO;

    @Mock
    TagServiceImpl tagService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        mapper = new CertificateModelMapperImpl();
        certificateValidator = new CertificateValidator();
        certificateService = new CertificateServiceImpl();
        certificateService.setCertificateDao(certificateDAO);
        certificateService.setValidator(certificateValidator);
        certificateService.setTagDao(tagDAO);
        tagService.setTagDAO(tagDAO);
        certificateService.setTagService(tagService);
        handler = new CertificateHandler();
        certificateService.setHandler(handler);
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
        certificateService.setMapper(mapper);
        certificateClientModel = mapper.certificateToCertificateClientModel(addedCertificate);
        tags = Arrays.asList(new TagEntity(1, "a"), new TagEntity(2, "a"));
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
                Arrays.asList(mapper.certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void findCertificateByIdTest() {
        certificateClientModel.setTags(new ArrayList<>());
        when(certificateDAO.findById(1))
                .thenReturn(Optional.ofNullable(addedCertificate));
        assertEquals(certificateService.findCertificateById(1),
                certificateClientModel);
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
        certificateClientModel.setTags(new ArrayList<>());
        when(certificateDAO.findByNamePart("a"))
                .thenReturn(Arrays.asList(addedCertificate));
        assertEquals(certificateService.findByName("a", null),
                Arrays.asList(certificateClientModel));
    }

    @Test
    void findByTagNameTest() {
        when(certificateDAO.findByTagName("a"))
                .thenReturn(Arrays.asList(addedCertificate));
        assertEquals(certificateService.findByTagName("a", null),
                Arrays.asList(mapper.certificateToCertificateClientModel(addedCertificate)));
    }

    @Test
    void updateTest() {
        when(certificateDAO.findByNamePart(addedCertificate.getName()))
                .thenReturn(Arrays.asList(addedCertificate));
        when(certificateDAO.update(addedCertificate))
                .thenReturn(true);
        assertEquals(certificateService.update(certificateClientModel),
                        certificateClientModel);
    }

    @Test
    void updateTestForThrowing() {
        when(certificateDAO.findByNamePart(addedCertificate.getName()))
                .thenReturn(new ArrayList<>());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.update(mapper
                        .certificateToCertificateClientModel(addedCertificate)));
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