package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.handler.CertificateHandler;
import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.mapper.CertificateModelMapper;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.PaginationParametersValidator;
import com.epam.esm.validator.exception.DuplicateCertificateException;
import com.epam.esm.validator.exception.UnknownCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.handler.RequestParameter.*;

/**
 * The type Certificate service.
 */
@Service
public class CertificateServiceImpl implements CertificateService {
    private static final String UNKNOWN = "nonexistent.certificate";
    private static final String DUPLICATE = "duplicate.certificate";
    private static final String BAD_PARAM = "bad.param";
    private CertificateModelMapper mapper;
    private CertificateDao certificateDao;
    private CertificateValidator validator;
    private PaginationParametersValidator paginationParametersValidator;
    private CertificateHandler handler;
    private TagService tagService;

    @Override
    public List<CertificateClientModel> findAllByParameter(Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (isRequiredParameter(parameter.getKey())) {
                parameters.remove(parameter.getKey());
                switch (parameter.getKey()) {
                    case NAME:
                        return findByName(parameter.getValue(), parameters);
                    case ID:
                        return Collections.singletonList(
                                findById(Integer.parseInt(parameter.getValue())));
                    default:
                        return findByTagName(parameter.getValue(),
                                parameters);
                }
            }
        }
        throw new BadParameterException(BAD_PARAM);
    }

    @Override
    @Transactional
    public CertificateClientModel add(CertificateClientModel certificate) {
        validator.validate(certificate);
        duplicateValidation(certificate);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificateDao.create(
                mapper.toEntity(certificate));
        certificate.setId(getIdByCertificateName(certificate));
        for (TagClientModel tag : certificate.getTags()) {
            tagService.addIfNotExist(tag);
            certificateDao.createLink(tagService.readByName(tag.getName()).getId(),
                    certificate.getId());
        }
        certificate.setTags(certificate.getTags()
                .stream()
                .map(a -> tagService.readByName(a.getName()))
                .collect(Collectors.toList()));
        return certificate;
    }

    @Override
    public List<CertificateClientModel> findAll(Map<String, String> parameters) {
        paginationParametersValidator.validate(parameters);
        List<CertificateClientModel>  certificates = certificateDao.findAll(Long.parseLong(parameters.remove(PAGE)),
                    Long.parseLong(parameters.remove(PAGE_SIZE)))
                .stream().map(a -> mapper.toClientModel(a))
                .collect(Collectors.toList());
        return sort(certificates, parameters);
    }

    @Override
    public CertificateClientModel findById(long id) {
        Optional<CertificateEntity> certificate = certificateDao.findById(id);
        if (certificate.isPresent()) {
            return mapper.toClientModel(certificate.get());
        }
        throw new UnknownCertificateException(UNKNOWN + "/id=" + id);
    }

    @Override
    public List<CertificateClientModel> findByName(String name, Map<String, String> parameters) {
        paginationParametersValidator.validate(parameters);
        List<CertificateClientModel> certificates = certificateDao.findAllByNameContainingIgnoreCase(name,
                Long.parseLong(parameters.remove(PAGE)),
                Long.parseLong( parameters.remove(PAGE_SIZE)))
                .stream().map(a -> mapper.toClientModel(a))
                .collect(Collectors.toList());
        if (certificates.isEmpty()) {
            throw new UnknownCertificateException(UNKNOWN + "/name=" + name);
        }
        return sort(certificates, parameters);
    }

    @Override
    public List<CertificateClientModel> findByTagName(String name, Map<String, String> parameters) {
        tagService.readByName(name);
        paginationParametersValidator.validate(parameters);
        List<CertificateClientModel> certificates = certificateDao.findAllByTags(name,
                Long.parseLong(parameters.remove(PAGE)),
                Long.parseLong( parameters.remove(PAGE_SIZE)))
                .stream().map(a -> mapper.toClientModel(a))
                .collect(Collectors.toList());
        if (certificates.isEmpty()) {
            throw new UnknownCertificateException(UNKNOWN + "/tag=" + name);
        }
        return sort(certificates, parameters);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CertificateClientModel update(CertificateClientModel certificate) {
        validator.validate(certificate);
        CertificateClientModel searchedCertificate = findById(certificate.getId());
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificate.setCreateDate(searchedCertificate.getCreateDate());
        updateTags(certificate, searchedCertificate);
        certificateDao.update(mapper.toEntity(certificate));
        return certificate;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteById(long id) {
        CertificateClientModel certificate = findById(id);
        for (TagClientModel tag : certificate.getTags()) {
            certificateDao.deleteLink(tag.getId(), certificate.getId());
        }
        certificateDao.delete(id);
        return true;
    }

    private void duplicateValidation(CertificateClientModel certificate) {
        Optional<CertificateEntity> certificateEntity = certificateDao.findByName(certificate.getName());
        if (certificateEntity.isPresent()) {
            throw new DuplicateCertificateException(DUPLICATE);
        }
    }

    private List<CertificateClientModel> sort(List<CertificateClientModel> certificates, Map<String, String> parameters) {
        if (parameters != null) {
            return handler.sortByParameters(certificates, parameters);
        }
        return certificates;
    }

    private boolean isRequiredParameter(String parameter) {
        return parameter.equals(NAME) ||
                parameter.equals(TAG) ||
                parameter.equals(ID);
    }

    private void updateTags(CertificateClientModel certificate,
                                         CertificateClientModel existCertificate) {
        for (TagClientModel tag : existCertificate.getTags()) {
            if (certificate.getTags()
                    .stream()
                    .noneMatch(a -> a.getName().equals(tag.getName()))) {
                certificateDao.deleteLink(tagService.readByName(tag.getName()).getId(), certificate.getId());
            }
        }
        for (TagClientModel tag : certificate.getTags()) {
            if (existCertificate.getTags()
                    .stream()
                    .noneMatch(a -> a.getName().equals(tag.getName()))) {
                certificateDao.createLink(tagService.addIfNotExist(tag).getId(),
                        certificate.getId());
            }
        }
        certificate.setTags(certificate.getTags()
                .stream()
                .map(a -> tagService.readByName(a.getName()))
                .collect(Collectors.toList()));
    }

    private long getIdByCertificateName (CertificateClientModel certificate) {
        Optional<CertificateEntity> certificateEntity = certificateDao.findByName(certificate.getName());
        if (certificateEntity.isPresent()) {
            return certificateEntity.get().getId();
        }
        throw new UnknownCertificateException(UNKNOWN + "/name=" + certificate.getName());
    }

    /**
     * Sets validator.
     *
     * @param validator the validator
     */
    @Autowired
    public void setValidator(CertificateValidator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setMapper(CertificateModelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Sets tag service.
     *
     * @param tagService the tag service
     */
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setPaginationParametersValidator(PaginationParametersValidator paginationParametersValidator) {
        this.paginationParametersValidator = paginationParametersValidator;
    }

    @Autowired
    public void setHandler(CertificateHandler handler) {
        this.handler = handler;
    }

    @Autowired
    public void setCertificateDao(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }
}
