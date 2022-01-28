package com.epam.esm.validator;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CertificateValidator implements Validator<CertificateEntity> {
    @Override
    public void validate(CertificateEntity certificate) throws ValidatorException {
        if (certificate == null) {
            throw new ValidatorException("Certificate cannot equals null");
        } else if (certificate.getName() == null) {
            throw new ValidatorException("Certificate's name cannot equals null");
        } else if (certificate.getDescription() == null) {
            throw new ValidatorException("Certificate's description cannot equals null");
        }else if (certificate.getDuration() <= 0) {
            throw new ValidatorException("Certificate's duration must be > 0");
        }else if (certificate.getPrice() <= 0) {
            throw new ValidatorException("Certificate's price must be > 0");
        }else if (certificate.getCreateDate() == null) {
            throw new ValidatorException("Certificate's create date cannot equals null");
        }else if (certificate.getLastUpdateDate() == null) {
            throw new ValidatorException("Certificate's last update date cannot equals null");
        }
    }
}
