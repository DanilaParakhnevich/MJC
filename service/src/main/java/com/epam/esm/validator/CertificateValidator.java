package com.epam.esm.validator;

import com.epam.esm.CertificateDAO;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.validator.exception.InvalidCertificateDataException;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CertificateValidator implements Validator<CertificateEntity> {
    private static final String INVALID_DATA = "invalid.certificate";
    @Autowired
    CertificateDAO certificateDAO;

    @Override
    public void validate(CertificateEntity certificate) throws ValidatorException {
        if (certificate == null || certificate.getName() == null
                || certificate.getDescription() == null || certificate.getDuration() <= 0
                || certificate.getPrice() <= 0 || certificate.getCreateDate() == null
                || certificate.getLastUpdateDate() == null) {
            throw new InvalidCertificateDataException(INVALID_DATA);
        }
    }
}
