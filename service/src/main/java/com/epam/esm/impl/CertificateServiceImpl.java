package com.epam.esm.impl;

import com.epam.esm.CertificateDAO;
import com.epam.esm.CertificateService;
import com.epam.esm.TagDAO;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateClientModelMapper;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class CertificateServiceImpl implements CertificateService {
    private static final String CANNOT_ADD = "Cannot add this certificate";
    private static final String CANNOT_FIND_BY_ID = "Cannot find certificate" +
            " with this id";
    private static final String CANNOT_FIND_BY_TAG = "Cannot find certificate" +
            " with this tag";
    @Autowired
    private CertificateDAO certificateDAO;
    @Autowired
    private CertificateValidator validator;
    @Autowired
    private TagDAO tagDAO;

    @Override
    public CertificateClientModel addCertificate(CertificateEntity certificate) {
        try {
            validator.validate(certificate);
            Optional<CertificateEntity> certificateEntity = certificateDAO.add(certificate);
            if (certificateEntity.isPresent()) {
                return CertificateClientModelMapper.INSTANCE
                        .certificateToCertificateClientModel(certificateEntity.get());
            }
        } catch (ValidatorException | ParseException e) {
            throw new SecurityException(e);
        }
        throw new SecurityException(CANNOT_ADD);
    }

    @Override
    public boolean addTagToCertificate(long certificateId, long tagId) {
        return certificateDAO.addTagToCertificate(certificateId, tagId);
    }

    @Override
    public List<CertificateClientModel> findAll() {
        return certificateDAO.findAll().stream()
                .map(a -> {
                    a.setTags(tagDAO.findByCertificateId(a.getId()));
                    return CertificateClientModelMapper
                            .INSTANCE.certificateToCertificateClientModel(a);
                }).collect(Collectors.toList());
    }

    @Override
    public CertificateClientModel findCertificateById(long id) throws ServiceException {
        Optional<CertificateEntity> certificate = certificateDAO.findById(id);
        if (certificate.isPresent()) {
            certificate.get().setTags(tagDAO.findByCertificateId(id));
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(certificate.get());
        }
        throw new ServiceException(CANNOT_FIND_BY_ID);
    }

    @Override
    public List<CertificateClientModel> findByName(String name) {
        return certificateDAO.findByNamePart(name).stream().map(a -> {
            a.setTags(tagDAO.findByCertificateId(a.getId()));
            return CertificateClientModelMapper.INSTANCE.certificateToCertificateClientModel(a);
        }).collect(Collectors.toList());
    }

    @Override
    public CertificateClientModel findByTag(TagEntity tag) throws ServiceException {
        Optional<CertificateEntity> certificate = certificateDAO.findByTag(tag);
        if (certificate.isPresent()) {
            certificate.get().setTags(new ArrayList<>());
            certificate.get().getTags().add(tag);
            return CertificateClientModelMapper.INSTANCE
                    .certificateToCertificateClientModel(certificate.get());
        }
        throw new ServiceException(CANNOT_FIND_BY_TAG);
    }

    @Override
    public boolean removeAllTagsByCertificateId(long id) {
        return certificateDAO.clearTagsByCertificate(id);
    }

    public void setCertificateValidator(CertificateValidator validator) {
        this.validator = validator;
    }

    public void setCertificateDAO(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }
}
