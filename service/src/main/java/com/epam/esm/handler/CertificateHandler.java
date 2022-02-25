package com.epam.esm.handler;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.handler.exception.BadParameterException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Certificate handler.
 */
@Component
public class CertificateHandler {
    private static final String DIRECTION = "direction";
    private static final String BAD_PARAMETER = "bad.param";
    private final Comparator<CertificateClientModel> comparatorByName;
    private final Comparator<CertificateClientModel> comparatorByCreateDate;

    public CertificateHandler() {
        comparatorByName
                = Comparator.comparing(CertificateClientModel::getName);
        comparatorByCreateDate
                = Comparator.comparing(CertificateClientModel::getCreateDate);
    }

    /**
     * Sort by parameters list.
     * exist parameters are : direction and param
     *
     * @param certificates the certificates
     * @param parameters   the parameters
     * @return the list
     */
    public List<CertificateClientModel> sortByParameters (List<CertificateClientModel> certificates, Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (!parameter.getKey().equals(DIRECTION)) {
                certificates = certificates.stream()
                        .sorted(handleParameter(parameter))
                        .collect(Collectors.toList());
            }
        }
        if (parameters.get(DIRECTION) != null &&
                parameters.get(DIRECTION).equals("DESC")) {
            reverse(certificates);
        }
        return certificates;
    }

    private void reverse (List<CertificateClientModel> certificates) {
        Collections.reverse(certificates);
    }

    private Comparator<CertificateClientModel> handleParameter (Map.Entry<String, String> parameter) throws BadParameterException {
        if (parameter.getKey().equals("param")) {
            switch (parameter.getValue()) {
                case "by-name":
                    return comparatorByName;
                case "by-create-date":
                    return comparatorByCreateDate;
            }
        }
        throw new BadParameterException(BAD_PARAMETER);
    }
}
