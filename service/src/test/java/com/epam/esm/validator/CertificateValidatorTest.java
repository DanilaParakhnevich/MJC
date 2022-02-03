package com.epam.esm.validator;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.validator.exception.BadDescriptionException;
import com.epam.esm.validator.exception.BadDurationException;
import com.epam.esm.validator.exception.BadNameException;
import com.epam.esm.validator.exception.BadPriceException;
import org.junit.jupiter.api.*;


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
        certificate.setPrice(1);
        certificate.setDuration(1);
        certificate.setDescription("a");
    }

    @Test
    void validateTest1() {
        certificate.setName("");
        Assertions.assertThrows(BadNameException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest2() {
        certificate.setDescription("");
        Assertions.assertThrows(BadDescriptionException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest3() {
        certificate.setDuration(0);
        Assertions.assertThrows(BadDurationException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest4() {
        certificate.setPrice(0);
        Assertions.assertThrows(BadPriceException.class,
                () -> validator.validate(certificate));
    }
}