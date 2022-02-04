package com.epam.esm.handler;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CertificateHandlerTest {
    private static final String PARAM = "param";
    private static final String DIRECTION = "direction";
    private CertificateClientModel firstTestCertificate;
    private CertificateClientModel secondTestCertificate;
    private CertificateClientModel thirdTestCertificate;
    private List<CertificateClientModel> certificates;

    Map<String, String> parameters;
    @BeforeEach
    void init() {
        parameters = new HashMap<>();
        firstTestCertificate = new CertificateClientModel(1, "A", "A",
                30, (short) 30, LocalDateTime.of(2021, 1, 10, 13, 5, 7),
                LocalDateTime.of(2021, 1, 10, 13, 5, 7), new ArrayList<>());
        secondTestCertificate = new CertificateClientModel(2, "C", "A",
                70, (short) 30, LocalDateTime.of(2022, 2, 15, 11, 6, 23),
                LocalDateTime.of(2022, 2, 15, 11, 6, 23), new ArrayList<>());
        thirdTestCertificate = new CertificateClientModel(3, "B", "A",
                100, (short) 60, LocalDateTime.of(2021, 12, 30, 5, 37, 48),
                LocalDateTime.of(2021, 12, 30, 5, 37, 48), new ArrayList<>());
        certificates = Arrays.asList(firstTestCertificate, secondTestCertificate, thirdTestCertificate);
    }


    @Test
    void sortByDirection() {
        parameters.put(DIRECTION, "DESC");
        Assertions.assertEquals(CertificateHandler.sortByParameters(certificates, parameters),
                Arrays.asList(thirdTestCertificate, secondTestCertificate, firstTestCertificate));
    }

    @Test
    void sortByName() {
        parameters.put(PARAM, "by-name");
        Assertions.assertEquals(CertificateHandler.sortByParameters(certificates, parameters),
                Arrays.asList(firstTestCertificate, thirdTestCertificate, secondTestCertificate));
    }

    @Test
    void sortByCreateDate() {
        parameters.put(PARAM, "by-create-date");
        Assertions.assertEquals(CertificateHandler.sortByParameters(certificates, parameters),
                Arrays.asList(firstTestCertificate, thirdTestCertificate, secondTestCertificate));
    }

    @Test
    void sortByCreateDateAndByDirection() {
        parameters.put(DIRECTION, "DESC");
        parameters.put(PARAM, "by-create-date");
        Assertions.assertEquals(CertificateHandler.sortByParameters(certificates, parameters),
                Arrays.asList(secondTestCertificate, thirdTestCertificate, firstTestCertificate));
    }
}