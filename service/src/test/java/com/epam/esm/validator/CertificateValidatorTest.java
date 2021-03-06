package com.epam.esm.validator;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.validator.exception.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateValidatorTest {
    CertificateValidator validator;
    CertificateClientModel certificate;

    @BeforeAll
    void init() {
        validator = new CertificateValidator();
        certificate = new CertificateClientModel();
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
        assertThrows(ValidatorException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest2() {
        certificate.setDescription("");
        assertThrows(ValidatorException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest3() {
        certificate.setDuration(0);
        assertThrows(ValidatorException.class,
                () -> validator.validate(certificate));
    }

    @Test
    void validateTest4() {
        certificate.setPrice(new BigDecimal(0));
        assertThrows(ValidatorException.class,
                () -> validator.validate(certificate));
    }
}