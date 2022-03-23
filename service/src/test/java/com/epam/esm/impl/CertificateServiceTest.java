package com.epam.esm.impl;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.handler.CertificateHandler;
import com.epam.esm.mapper.CertificateModelMapper;
import com.epam.esm.mapper.CertificateModelMapperImpl;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

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
    CertificateDao certificateRepository;

    @Mock
    TagDao tagRepository;

    @Mock
    TagServiceImpl tagService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        mapper = new CertificateModelMapperImpl();
        certificateValidator = new CertificateValidator();
        certificateService = new CertificateServiceImpl();
        certificateService.setCertificateDao(certificateRepository);
        certificateService.setValidator(certificateValidator);
        tagService.setTagDao(tagRepository);
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
        certificateClientModel = mapper.toClientModel(addedCertificate);
        tags = Arrays.asList(new TagEntity(1, "a"), new TagEntity(2, "a"));
    }


    @Test
    void findAllTest() {
        addedCertificate.setTags(tags);
        when(certificateRepository.findAll())
                .thenReturn(Collections.singletonList(addedCertificate));
        assertEquals(certificateService.findAll(null),
                Collections.singletonList(mapper.toClientModel(addedCertificate)));
    }

    @Test
    void findCertificateByIdTest() {
        certificateClientModel.setTags(new ArrayList<>());
        when(certificateRepository.findById(1L))
                .thenReturn(Optional.ofNullable(addedCertificate));
        assertEquals(certificateService.findById(1),
                certificateClientModel);
    }

    @Test
    void findCertificateByIdTestForThrowing() {
        when(certificateRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.findById(1));
    }

    @Test
    void findByNameTest() {
        certificateClientModel.setTags(new ArrayList<>());
        when(certificateRepository.findAllByTags("a"))
                .thenReturn(Collections.singletonList(addedCertificate));
        assertEquals(certificateService.findByName("a", null),
                Collections.singletonList(certificateClientModel));
    }

    @Test
    void findByTagNameTest() {
        when(certificateRepository.findAllByTags("a"))
                .thenReturn(Collections.singletonList(addedCertificate));
        assertEquals(certificateService.findByTagName("a", null),
                Collections.singletonList(mapper.toClientModel(addedCertificate)));
    }

    @Test
    void updateTest() {
        when(certificateRepository.findAllByNameContainingIgnoreCase(addedCertificate.getName()))
                .thenReturn(Collections.singletonList(addedCertificate));
        when(certificateRepository.save(addedCertificate))
                .thenReturn(addedCertificate);
        assertEquals(certificateService.update(certificateClientModel),
                        certificateClientModel);
    }

    @Test
    void updateTestForThrowing() {
        when(certificateRepository.findAllByNameContainingIgnoreCase(addedCertificate.getName()))
                .thenReturn(new ArrayList<>());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.update(mapper
                        .toClientModel(addedCertificate)));
    }

    @Test
    void deleteByIdTest() {
        when(certificateRepository.findById(addedCertificate.getId()))
                .thenReturn(Optional.ofNullable(addedCertificate));
        assertTrue(certificateService.deleteById(addedCertificate.getId()));
    }

    @Test
    void deleteByIdForThrowing() {
        when(certificateRepository.findById(addedCertificate.getId()))
                .thenReturn(Optional.empty());
        assertThrows(UnknownCertificateException.class,
                () -> certificateService.deleteById(addedCertificate.getId()));
    }
}