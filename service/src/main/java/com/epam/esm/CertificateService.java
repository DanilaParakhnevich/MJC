package com.epam.esm;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;

import java.util.List;

public interface CertificateService extends Service{
    CertificateClientModel addCertificate(CertificateEntity certificate);

    List<CertificateClientModel> findAll();

    CertificateClientModel findCertificateById();

    CertificateClientModel findByTagName(String tagName);

    boolean removeTag(TagClientModel tag, long certificateId);
}
