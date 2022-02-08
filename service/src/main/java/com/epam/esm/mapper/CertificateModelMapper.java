package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Certificate client model mapper.
 */
@Component
@Mapper(componentModel = "spring")
public interface CertificateModelMapper {
    /**
     * Certificate to certificate client model certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    CertificateClientModel certificateToCertificateClientModel(CertificateEntity certificate);

    CertificateEntity certificateClientModelToCertificate(CertificateClientModel certificate);

}
