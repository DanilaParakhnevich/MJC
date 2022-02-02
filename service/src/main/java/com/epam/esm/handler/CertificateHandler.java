package com.epam.esm.handler;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.handler.exception.BadParameterException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CertificateHandler {
    private static final String BAD_PARAMETER = "bad.param";
    private static final Comparator<CertificateClientModel> comparatorByName
            = Comparator.comparing(CertificateClientModel::getName);
    private static final Comparator<CertificateClientModel> comparatorByCreateDate
            = Comparator.comparing(CertificateClientModel::getCreateDate);

    private CertificateHandler(){}

    public static List<CertificateClientModel> sortByParameters (List<CertificateClientModel> certificates, Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (!parameter.getKey().equals("direction")) {
                certificates = certificates.stream()
                        .sorted(handleParameter(parameter))
                        .collect(Collectors.toList());
            }
        }
        if (parameters.get("direction") != null &&
                parameters.get("direction").equals("DESC")) {
            reverse(certificates);
        }
        return certificates;
    }

    private static void reverse (List<CertificateClientModel> certificates) {
        Collections.reverse(certificates);
    }

    private static Comparator<CertificateClientModel> handleParameter (Map.Entry<String, String> parameter) {
        switch (parameter.getValue()) {
            case "by-name" : return comparatorByName;
            case "by-create-date" : return comparatorByCreateDate;
            default: throw new BadParameterException(BAD_PARAMETER);
        }
    }
}
