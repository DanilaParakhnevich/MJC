package com.epam.esm;

import com.epam.esm.dto.CertificateClientModel;

import java.util.List;
import java.util.Map;

/**
 * The interface Certificate service.
 */
public interface CertificateService {
    /**
     * Add certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    CertificateClientModel add(CertificateClientModel certificate);

    /**
     * Add tag to certificate boolean.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     * @return the boolean
     */
    boolean addTagToCertificate(long certificateId, long tagId);

    /**
     * Find all list.
     *
     * @param parameters the parameters
     * @return the list
     */
    List<CertificateClientModel> findAll(Map<String, String> parameters);

    /**
     * Find certificate by id certificate client model.
     *
     * @param id the id
     * @return the certificate client model
     */
    CertificateClientModel findById(long id) ;

    /**
     * Find by name list.
     *
     * @param name       the name
     * @param parameters the parameters
     * @return the list
     */
    List<CertificateClientModel> findByName(String name, Map<String, String> parameters);

    /**
     * Find by tag name list.
     *
     * @param name       the name
     * @param parameters the parameters
     * @return the list
     */
    List<CertificateClientModel> findByTagName(String name, Map<String, String> parameters) ;

    /**
     * Update certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    CertificateClientModel update (CertificateClientModel certificate) ;

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteById(long id);
}
