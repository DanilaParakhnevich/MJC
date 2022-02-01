package com.epam.esm;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;

import java.util.List;

public interface CertificateService extends Service{
    CertificateClientModel add(CertificateEntity certificate);

    boolean addTagToCertificate(long certificateId, long tagId);

    List<CertificateClientModel> findAll();

    CertificateClientModel findCertificateById(long id) ;

    List<CertificateClientModel> findByName(String name);

    List<CertificateClientModel> findByTagName(String name) ;

    CertificateClientModel update (CertificateEntity certificate) ;

    boolean deleteById(long id);
}