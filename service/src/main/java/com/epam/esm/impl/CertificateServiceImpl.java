package com.epam.esm.impl;

import com.epam.esm.CertificateDao;
import com.epam.esm.CertificateService;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.handler.CertificateHandler;
import com.epam.esm.mapper.CertificateModelMapper;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.DuplicateCertificateException;
import com.epam.esm.validator.exception.InvalidDateFormatException;
import com.epam.esm.validator.exception.UnknownCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
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
    private CertificateDao certificateDAO;
    private CertificateValidator validator;
    private CertificateHandler handler;
    private TagDao tagDAO;
    private TagService tagService;

    @Override
    @Transactional
    public CertificateClientModel add(CertificateClientModel certificate) {
        try {
            validator.validate(certificate);
            duplicateValidation(certificate);
            CertificateEntity certificateEntity
                    = findAvailable(certificateDAO.add(
                            mapper.certificateClientModelToCertificate(certificate)).get());
            if (certificate.getTags() != null) {
                for (TagClientModel tag : certificate.getTags()) {
                    addTagToCertificate(certificateEntity.getId(),
                            tagService.addIfNotExist(tag).getId());
                }
            }
            return findCertificateById(certificateEntity.getId());
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT, e);
        }

    }

    @Override
    public boolean addTagToCertificate(long certificateId, long tagId) {
        return certificateDAO.addTagToCertificate(certificateId, tagId);
    }

    @Override
    public List<CertificateClientModel> findAll(Map<String, String> parameters) {
        return sort(certificateDAO.findAll()
                .stream()
                .map(a -> {
                    a.setTags(tagDAO.findByCertificateId(a.getId()));
                    return mapper.certificateToCertificateClientModel(a);
                }).collect(Collectors.toList()), parameters);
    }

    @Override
    public CertificateClientModel findCertificateById(long id) {
        Optional<CertificateEntity> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            certificate.get().setTags(tagDAO
                    .findByCertificateId(certificate.get().getId()));
            return mapper.certificateToCertificateClientModel(certificate.get());
        }
        throw new UnknownCertificateException(UNKNOWN + "/id=" + id);
    }

    @Override
    public List<CertificateClientModel> findByName(String name, Map<String, String> parameters) {
        List<CertificateEntity> certificates = certificateDAO.findByNamePart(name);
        if (certificates.isEmpty()) {
            throw new UnknownCertificateException(UNKNOWN + "/name=" + name);
        }
        return sort(certificates.stream().map(a -> {
            a.setTags(tagDAO.findByCertificateId(a.getId()));
            return mapper.certificateToCertificateClientModel(a);
        }).collect(Collectors.toList()), parameters);
    }

    @Override
    public List<CertificateClientModel> findByTagName(
            String name,
            Map<String, String> parameters) {
        return sort(certificateDAO.findByTagName(name).stream().map(a -> {
            a.setTags(tagDAO.findByCertificateId(a.getId()));
            return mapper.certificateToCertificateClientModel(a);
        }).collect(Collectors.toList()), parameters);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CertificateClientModel update(CertificateClientModel certificate) {
        try {
            validator.validate(certificate);
            CertificateEntity finalCertificate =
                    findAvailable(mapper.certificateClientModelToCertificate(certificate));
            if (finalCertificate == null) {
                throw new UnknownCertificateException(UNKNOWN);
            }
            updateTags(finalCertificate);
            return mapper.certificateToCertificateClientModel(finalCertificate);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteById(long id) {
        CertificateClientModel certificate = findCertificateById(id);
        if (certificate.getTags() != null) {
            certificateDAO.clearTagsByCertificate(certificate.getId());
        }
        certificateDAO.delete(certificate.getId());
        return true;
    }

    private void duplicateValidation(CertificateClientModel certificate) {
        List<CertificateEntity> certificates = certificateDAO.findAll();
        if (certificates.stream()
                .anyMatch(a -> a.getName().equals(certificate.getName()))) {
            throw new DuplicateCertificateException(DUPLICATE);
        }
    }

    private CertificateEntity findAvailable (CertificateEntity certificate) {
        List<CertificateEntity> certificates = certificateDAO.findByNamePart(certificate.getName())
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

    private void updateTags(CertificateEntity certificate) throws ParseException {
        certificateDAO.update(certificate);
        certificateDAO.clearTagsByCertificate(certificate.getId());
        if (certificate.getTags() != null
                && !certificate.getTags().isEmpty()) {
            CertificateClientModel certificateClientModel =
                    mapper.certificateToCertificateClientModel(certificate);
            certificateClientModel.setTags(
                    certificateClientModel.getTags()
                    .stream()
                    .distinct()
                    .collect(Collectors.toList()));
            certificateClientModel.getTags().forEach(a ->
                    certificateDAO.addTagToCertificate(certificate.getId()
                            ,tagService.addIfNotExist(a).getId()));
            certificate.setTags(
                    tagDAO.findByCertificateId(certificate.getId()));
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
     * @param certificateDAO the certificate dao
     */
    @Autowired
    public void setCertificateDAO(CertificateDao certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    /**
     * Sets tag dao.
     *
     * @param tagDAO the tag dao
     */
    @Autowired
    public void setTagDAO(TagDao tagDAO) {
        this.tagDAO = tagDAO;
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
    public void setMapper(CertificateModelMapper mapper) {
        this.mapper = mapper;
    }
}
