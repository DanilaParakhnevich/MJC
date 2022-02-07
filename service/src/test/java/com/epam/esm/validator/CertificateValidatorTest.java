package com.epam.esm.validator;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.validator.exception.BadDescriptionException;
import com.epam.esm.validator.exception.BadDurationException;
import com.epam.esm.validator.exception.BadNameException;
import com.epam.esm.validator.exception.BadPriceException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateValidatorTest {
    CertificateValidator validator;
    CertificateEntity certificate;

    @BeforeAll
    void init() {
        validator = new CertificateValidator();
        certificate = new CertificateEntity();
    }

    @BeforeEach
    void configuration() {
        certificate.setName("a");
        certificate.setPrice(new BigDecimal(0));
        certificate.setDuration(1);
        certificate.setDescription("a");
    }

    @Test
    void validateTest1() {
        certificate.setName("");
        assertThrows(BadNameException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest2() {
        certificate.setDescription("");
        assertThrows(BadDescriptionException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest3() {
        certificate.setDuration(0);
        assertThrows(BadDurationException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest4() {
        certificate.setPrice(new BigDecimal(0));
        assertThrows(BadPriceException.class,
                () -> validator.validate(certificate));
    }
}