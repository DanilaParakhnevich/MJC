package com.epam.esm.validator;

import com.epam.esm.CertificateDAO;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.validator.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CertificateValidator implements Validator<CertificateEntity> {
    private static final String INVALID_DATA = "invalid.certificate";
    private static final String BAD_NAME = "bad.value.name";
    private static final String BAD_DESCRIPTION = "bad.value.description";
    private static final String BAD_DURATION = "bad.value.duration";
    private static final String BAD_PRICE = "bad.value.price";


    @Autowired
    CertificateDAO certificateDAO;

    @Override
    public void validate(CertificateEntity certificate) throws ValidatorException {
        if (certificate == null) {
            throw new InvalidCertificateDataException(INVALID_DATA + "certificate=null");
        } else if (certificate.getName() == null) {
            throw new BadNameException(BAD_NAME + "/name=null");
        } else if (certificate.getName().isEmpty()) {
            throw new BadNameException(BAD_NAME + "/name's length=0");
        } else if (certificate.getDescription() == null) {
            throw new BadDescriptionException(BAD_DESCRIPTION + "/description=null");
        } else if (certificate.getDescription().isEmpty()) {
            throw new BadDescriptionException(BAD_DESCRIPTION + "/description's length=0");
        }else if (certificate.getDuration() <= 0) {
            throw new BadDurationException(BAD_DURATION + "/duration=" + certificate.getDuration());
        } else if (certificate.getPrice() <= 0) {
            throw new BadPriceException(BAD_PRICE + "/price=" + certificate.getPrice());
        }
    }
}
