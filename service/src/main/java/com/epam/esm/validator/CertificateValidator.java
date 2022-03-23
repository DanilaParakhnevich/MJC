package com.epam.esm.validator;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.validator.exception.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * The type Certificate validator.
 */
@Component
public class CertificateValidator implements Validator<CertificateClientModel> {
    private static final String INVALID_DATA = "invalid.certificate";
    private static final String BAD_NAME = "bad.value.name";
    private static final String BAD_DESCRIPTION = "bad.value.description";
    private static final String BAD_DURATION = "bad.value.duration";
    private static final String BAD_PRICE = "bad.value.price";

    @Override
    public void validate(CertificateClientModel certificate) throws ValidatorException {
        if (certificate == null) {
            throw new ValidatorException(INVALID_DATA + "/certificate=null");
        } else if (certificate.getName() == null) {
            throw new ValidatorException(BAD_NAME + "/name=null");
        } else if (certificate.getName().isEmpty()) {
            throw new ValidatorException(BAD_NAME + "/name's length=0");
        } else if (certificate.getDescription() == null) {
            throw new ValidatorException(BAD_DESCRIPTION + "/description=null");
        } else if (certificate.getDescription().isEmpty()) {
            throw new ValidatorException(BAD_DESCRIPTION + "/description's length=0");
        } else if (certificate.getDuration() <= 0) {
            throw new ValidatorException(BAD_DURATION + "/duration=" + certificate.getDuration());
        } else if (certificate.getPrice() == null) {
            throw new ValidatorException(BAD_PRICE + "/price=null");
        } else if (certificate.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            throw new ValidatorException(BAD_PRICE + "/price=" + certificate.getPrice());
        }
    }
}
