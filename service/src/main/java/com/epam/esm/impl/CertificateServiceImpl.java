package com.epam.esm.impl;

import com.epam.esm.CertificateDao;
import com.epam.esm.CertificateService;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.handler.CertificateHandler;
import com.epam.esm.mapper.CertificateModelMapper;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.DuplicateCertificateException;
import com.epam.esm.validator.exception.InvalidDateFormatException;
import com.epam.esm.validator.exception.UnknownCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Certificate service.
 */
@Service
public class CertificateServiceImpl implements CertificateService {
    private static final String INVALID_DATE_FORMAT = "invalid.date.format";
    private static final String UNKNOWN = "nonexistent.certificate";
    private static final String DUPLICATE = "duplicate.certificate";
    private CertificateModelMapper mapper;
    private TagModelMapper tagMapper;

    private CertificateDao certificateDao;
    private CertificateValidator validator;
    private CertificateHandler handler;
    private TagDao tagDao;
    private TagService tagService;

    @Override
    @Transactional
    public CertificateClientModel add(CertificateClientModel certificate) {
        try {
            validator.validate(certificate);
            duplicateValidation(certificate);
            certificate.setCreateDate(LocalDateTime.now());
            certificate.setLastUpdateDate(LocalDateTime.now());
            CertificateEntity certificateEntity
                    = findAvailable(certificateDao.add(
                            mapper.toEntity(certificate)).get());
            if (certificate.getTags() != null) {
                for (TagClientModel tag : certificate.getTags()) {
                    addTagToCertificate(certificateEntity.getId(),
                            tagService.addIfNotExist(tag).getId());
                }
            }
            return findById(certificateEntity.getId());
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT, e);
        }

    }

    @Override
    public boolean addTagToCertificate(long certificateId, long tagId) {
        return certificateDao.addTagToCertificate(certificateId, tagId);
    }

    @Override
    public List<CertificateClientModel> findAll(Map<String, String> parameters) {
        return sort(certificateDao.findAll()
                .stream()
                .map(a -> {
                    a.setTags(tagDao.findByCertificateId(a.getId()));
                    return mapper.toClientModel(a);
                }).collect(Collectors.toList()), parameters);
    }

    @Override
    public CertificateClientModel findById(long id) {
        Optional<CertificateEntity> certificate = certificateDao.findById(id);
        if (certificate.isPresent()) {
            certificate.get().setTags(tagDao
                    .findByCertificateId(certificate.get().getId()));
            return mapper.toClientModel(certificate.get());
        }
        throw new UnknownCertificateException(UNKNOWN + "/id=" + id);
    }

    @Override
    public List<CertificateClientModel> findByName(String name, Map<String, String> parameters) {
        List<CertificateEntity> certificates = certificateDao.findByNamePart(name);
        if (certificates.isEmpty()) {
            throw new UnknownCertificateException(UNKNOWN + "/name=" + name);
        }
        return sort(certificates.stream().map(a -> {
            a.setTags(tagDao.findByCertificateId(a.getId()));
            return mapper.toClientModel(a);
        }).collect(Collectors.toList()), parameters);
    }

    @Override
    public List<CertificateClientModel> findByTagName(
            String name,
            Map<String, String> parameters) {
        List<CertificateEntity> certificates = certificateDao.findByTagName(name);
        if (certificates.isEmpty()) {
            throw new UnknownCertificateException(UNKNOWN + "/tag=" + name);
        }
        return sort(certificates.stream().map(a -> {
            a.setTags(tagDao.findByCertificateId(a.getId()));
            return mapper.toClientModel(a);
        }).collect(Collectors.toList()), parameters);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CertificateClientModel update(CertificateClientModel certificate) {
        try {
            validator.validate(certificate);
            Optional<CertificateEntity> searchedCertificate = certificateDao.findById(certificate.getId());
            if (!searchedCertificate.isPresent()) {
                throw new UnknownCertificateException(UNKNOWN + "/id=" + certificate.getId());
            }
            CertificateEntity clientModelCopy = mapper.toEntity(certificate);
            clientModelCopy.setLastUpdateDate(LocalDateTime.now());
            clientModelCopy.setCreateDate(searchedCertificate.get().getCreateDate());
            certificateDao.update(clientModelCopy);
            updateTags(clientModelCopy);
            clientModelCopy.setTags(tagDao.findByCertificateId(clientModelCopy.getId()));
            return mapper.toClientModel(clientModelCopy);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteById(long id) {
        CertificateClientModel certificate = findById(id);
        if (certificate.getTags() != null) {
            certificateDao.clearTagsByCertificate(certificate.getId());
        }
        certificateDao.delete(certificate.getId());
        return true;
    }

    private void duplicateValidation(CertificateClientModel certificate) {
        List<CertificateEntity> certificates = certificateDao.findAll();
        if (certificates.stream()
                .anyMatch(a -> a.getName().equals(certificate.getName()))) {
            throw new DuplicateCertificateException(DUPLICATE);
        }
    }

    private CertificateEntity findAvailable (CertificateEntity certificate) {
        List<CertificateEntity> certificates = certificateDao.findByNamePart(certificate.getName())
                .stream()
                .filter(a -> a.getName().equals(certificate.getName()))
                .collect(Collectors.toList());
        if (certificates.isEmpty()) {
            return null;
        }
        if (certificate.getTags() != null) {
            certificates.get(0).setTags(certificate.getTags());
        }
        return certificates.get(0);
    }

    private List<CertificateClientModel> sort(List<CertificateClientModel> certificates, Map<String, String> parameters) {
        if (parameters != null) {
            return handler.sortByParameters(certificates, parameters);
        }
        return certificates;
    }

    private void updateTags(CertificateEntity certificate) {
        List<TagEntity> oldTags = tagDao.findByCertificateId(certificate.getId());
        for (TagEntity tag : oldTags) {
            if (certificate.getTags()
                    .stream()
                    .noneMatch(a -> a.getName().equals(tag.getName()))) {
                certificateDao.deleteTagFromCertificate(certificate.getId(), tag.getId());
                oldTags.remove(tag);
            }
        }
        for (TagEntity tag : certificate.getTags()) {
            if (oldTags.stream().noneMatch(a -> a.getName().equals(tag.getName()))) {
                certificateDao.addTagToCertificate(certificate.getId(),
                        tagService.addIfNotExist(tagMapper.toClientModel(tag)).getId());
            }
        }
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

    /**
     * Sets certificate dao.
     *
     * @param certificateDao the certificate dao
     */
    @Autowired
    public void setCertificateDao(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    /**
     * Sets tag dao.
     *
     * @param tagDao the tag dao
     */
    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
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
    public void setHandler(CertificateHandler handler) {
        this.handler = handler;
    }

    @Autowired
    public void setTagMapper(TagModelMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Autowired
    public void setMapper(CertificateModelMapper mapper) {
        this.mapper = mapper;
    }
}
