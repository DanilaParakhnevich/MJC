package com.epam.esm.impl;

import com.epam.esm.CertificateDao;
import com.epam.esm.CertificateService;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.handler.CertificateHandler;
import com.epam.esm.mapper.CertificateClientModelMapper;
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
    private CertificateDao certificateDAO;
    private CertificateValidator validator;
    private TagDao tagDAO;
    private TagService tagService;

    @Override
    @Transactional
    public CertificateClientModel add(CertificateEntity certificate) {
        try {
            validator.validate(certificate);
            duplicateValidation(certificate);
            CertificateEntity certificateEntity
                    = findAvailable(certificateDAO.add(certificate).get());
            if (certificate.getTags() != null) {
                for (TagEntity tag : certificate.getTags()) {
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
                    return CertificateClientModelMapper.INSTANCE
                            .certificateToCertificateClientModel(a);
                }).collect(Collectors.toList()), parameters);
    }

    @Override
    public CertificateClientModel findCertificateById(long id) {
        Optional<CertificateEntity> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            certificate.get().setTags(tagDAO
                    .findByCertificateId(certificate.get().getId()));
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(certificate.get());
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
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(a);
        }).collect(Collectors.toList()), parameters);
    }

    @Override
    public List<CertificateClientModel> findByTagName(
            String name,
            Map<String, String> parameters) {
        return certificateDAO.findByTagName(name).stream().map(a -> {
            a.setTags(tagDAO.findByCertificateId(a.getId()));
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(a);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CertificateClientModel update(CertificateEntity certificate) {
        try {
            validator.validate(certificate);
            CertificateEntity finalCertificate = findAvailable(certificate);
            if (finalCertificate == null) {
                throw new UnknownCertificateException(UNKNOWN);
            }
            updateTags(finalCertificate);
            return CertificateClientModelMapper
                    .INSTANCE.certificateToCertificateClientModel(finalCertificate);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteById(long id) {
        Optional<CertificateEntity> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            if (certificate.get().getTags() != null) {
                certificate.get().getTags().forEach(a ->
                        tagService.deleteById(a.getId()));
            }
            certificateDAO.delete(certificate.get().getId());
            return true;
        }
        throw new UnknownCertificateException(UNKNOWN + "/" + id);
    }

    private void duplicateValidation(CertificateEntity certificate) {
        List<CertificateEntity> certificates = certificateDAO.findAll();
        if (certificates.stream()
                .anyMatch(a -> a.getName().equals(certificate.getName()))) {
            throw new DuplicateCertificateException(DUPLICATE);
        }
    }

    private CertificateEntity findAvailable (CertificateEntity certificate) {
        List<CertificateEntity> certificates = certificateDAO.findByNamePart(certificate.getName());
        if (certificates.isEmpty()) {
            return null;
        }
        CertificateEntity finalCertificate = certificates.stream()
                .filter(a -> a.getName().equals(certificate.getName()))
                .collect(Collectors.toList()).get(0);
        if (certificate.getTags() != null) {
            finalCertificate.setTags(certificate.getTags());
        }
        return finalCertificate;
    }

    private List<CertificateClientModel> sort(List<CertificateClientModel> certificates, Map<String, String> parameters) {
        if (parameters != null) {
            return CertificateHandler.sortByParameters(certificates, parameters);
        }
        return certificates;
    }

    private void updateTags(CertificateEntity certificate) throws ParseException {
        certificateDAO.update(certificate);
        certificateDAO.clearTagsByCertificate(certificate.getId());
        if (certificate.getTags() != null
                && !certificate.getTags().isEmpty()) {
            certificate.setTags(certificate.getTags()
                    .stream()
                    .distinct()
                    .collect(Collectors.toList()));
            certificate.getTags().forEach(a ->
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
}
