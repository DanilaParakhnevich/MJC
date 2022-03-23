package com.epam.esm.dao.extractor;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateExtractorForPagination implements ResultSetExtractor<List<CertificateEntity>> {
    CertificateExtractor certificateExtractor;

    @Override
    public List<CertificateEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<CertificateEntity> certificates = new ArrayList<>();
        while (rs.next()) {
            CertificateEntity certificate = new CertificateEntity();
            certificateExtractor.fillCertificateFields(certificate, rs);
            certificates.add(certificate);
        }
        return certificates;
    }

    @Autowired
    public void setCertificateExtractor(CertificateExtractor certificateExtractor) {
        this.certificateExtractor = certificateExtractor;
    }
}
