package com.epam.esm;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;

import java.util.List;
import java.util.Map;

public interface CertificateService extends Service {
    CertificateClientModel add(CertificateEntity certificate);

    boolean addTagToCertificate(long certificateId, long tagId);

    List<CertificateClientModel> findAll(Map<String, String> parameters);

    CertificateClientModel findCertificateById(long id) ;

    List<CertificateClientModel> findByName(String name, Map<String, String> parameters);

    List<CertificateClientModel> findByTagName(String name, Map<String, String> parameters) ;

    CertificateClientModel update (CertificateEntity certificate) ;

    boolean deleteById(long id);
}
