package com.epam.esm.impl;

import com.epam.esm.CertificateDAO;
import com.epam.esm.CertificateService;
import com.epam.esm.TagDAO;
import com.epam.esm.TagService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.CertificateClientModelMapper;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.DuplicateCertificateException;
import com.epam.esm.validator.exception.InvalidDateFormatException;
import com.epam.esm.validator.exception.UnknownCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class CertificateServiceImpl implements CertificateService {
    private static final String INVALID_DATE_FORMAT = "invalid.date.format";
    private static final String UNKNOWN = "unknown.certificate";
    private static final String DUPLICATE = "duplicate.certificate";
    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private CertificateValidator validator;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    TagService tagService;

    @Override
    @Transactional
    public CertificateClientModel add(CertificateEntity certificate) {
        try {
            validator.validate(certificate);
            duplicateValidation(certificate);
            CertificateEntity certificateEntity = certificateDAO.add(certificate).get();
            if (certificate.getTags() != null) {
                for (TagEntity tag : certificate.getTags()) {
                    addTagToCertificate(certificateEntity.getId(),
                            tagService.add(tag).getId());
                }
            }
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT, e);
        }
        return CertificateClientModelMapper.INSTANCE
                .certificateToCertificateClientModel(certificate);
    }

    @Override
    public boolean addTagToCertificate(long certificateId, long tagId) {
        return certificateDAO.addTagToCertificate(certificateId, tagId);
    }

    @Override
    public List<CertificateClientModel> findAll() {
        return certificateDAO.findAll()
                .stream()
                .map(a -> {
                    a.setTags(tagDAO.findByCertificateId(a.getId()));
                    return CertificateClientModelMapper.INSTANCE
                            .certificateToCertificateClientModel(a);
                }).collect(Collectors.toList());
    }

    @Override
    public CertificateClientModel findCertificateById(long id) {
        Optional<CertificateEntity> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            certificate.get().setTags(tagDAO.findByCertificateId(certificate.get().getId()));
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(certificate.get());
        }
        throw new UnknownCertificateException(UNKNOWN);
    }

    @Override
    public List<CertificateClientModel> findByName(String name) {
        return certificateDAO.findByNamePart(name).stream().map(a -> {
            a.setTags(tagDAO.findByCertificateId(a.getId()));
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(a);
        }).collect(Collectors.toList());
    }

    @Override
    public List<CertificateClientModel> findByTagName(String name) {
        return certificateDAO.findByTagName(name).stream().map(a -> {
            a.setTags(tagDAO.findByCertificateId(a.getId()));
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(a);
        }).collect(Collectors.toList());
    }

    @Override
    public CertificateClientModel update(CertificateEntity certificate) {
        try {
            validator.validate(certificate);
            certificateDAO.update(certificate);
            certificateDAO.clearTagsByCertificate(certificate.getId());
            if (certificate.getTags() != null && !certificate.getTags().isEmpty()) {
                certificateDAO.clearTagsByCertificate(certificate.getId());
                certificate.getTags().forEach(a ->
                        certificateDAO.addTagToCertificate(certificate.getId()
                                ,tagService.addIfNotExist(a).getId()));
            }
            return CertificateClientModelMapper
                    .INSTANCE.certificateToCertificateClientModel(certificate);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT);
        }
    }

    @Override
    public boolean deleteById(long id) {
        Optional<CertificateEntity> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            if (certificate.get().getTags() != null) {
                certificate.get().getTags().forEach(a ->
                        tagService.deleteById(a.getId()));
            }
            certificateDAO.deleteById(certificate.get().getId());
            return true;
        }
        throw new UnknownCertificateException(UNKNOWN);
    }

    public void setCertificateValidator(CertificateValidator validator) {
        this.validator = validator;
    }

    public void setCertificateDAO(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    private void duplicateValidation(CertificateEntity certificate) {
        List<CertificateEntity> certificates = certificateDAO.findAll();
        if (certificates.stream().anyMatch(a -> a.getName().equals(certificate.getName()))) {
            throw new DuplicateCertificateException(DUPLICATE);
        }
    }
}
