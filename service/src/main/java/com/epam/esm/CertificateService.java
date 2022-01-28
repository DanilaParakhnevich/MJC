package com.epam.esm;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface CertificateService extends Service{
    CertificateClientModel addCertificate(CertificateEntity certificate);

    List<CertificateClientModel> findAll();

    CertificateClientModel findCertificateById(long id) throws ServiceException;

    List<CertificateClientModel> findByName(String name);

    CertificateClientModel findByTag(TagEntity tag) throws ServiceException;

    boolean removeAllTagsByCertificateId(long id);
}
