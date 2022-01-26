package com.epam.esm.service;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.Tag;

import java.util.List;

public interface CertificateService extends Service{
    Certificate addCertificate(Certificate certificate);

    List<Certificate> findAll();

    Certificate findCertificateById();

    Certificate findByTagName(String tagName);

    boolean removeTag(Tag tag);
}
