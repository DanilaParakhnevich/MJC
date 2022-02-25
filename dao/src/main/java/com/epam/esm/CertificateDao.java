package com.epam.esm;

import com.epam.esm.entity.CertificateEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * The abstract class Certificate dao.
 */
public interface CertificateDao {
    /**
     * Add optional.
     *
     * @param certificate the certificate
     * @return the optional of certificate entity
     * @throws ParseException the parse exception
     */
    Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException;

    /**
     * Find all method
     *
     * @return list of certificate entity
     */
    List<CertificateEntity> findAll();

    /**
     * Find by id method
     *
     * @param id the id
     * @return the optional of certificate entity
     */
    Optional<CertificateEntity> findById(long id);

    /**
     * Find by name part list.
     *
     * @param namePart the name part
     * @return the list
     */
    List<CertificateEntity> findByNamePart(String namePart);

    /**
     * Find by tag name list.
     *
     * @param name the name
     * @return the list
     */
    List<CertificateEntity> findByTagName(String name);

    /**
     * Update boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     * @throws ParseException the parse exception
     */
    boolean update (CertificateEntity certificate) throws ParseException;

    /**
     * Add tag to certificate boolean.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     * @return the boolean
     */
    boolean addTagToCertificate(long certificateId, long tagId);

    /**
     * Delete tag from certificate boolean.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     * @return the boolean
     */
    boolean deleteTagFromCertificate(long certificateId, long tagId);

    /**
     * Clear tags by certificate boolean.
     *
     * @param certificateId the certificate id
     * @return the boolean
     */
    boolean clearTagsByCertificate(long certificateId);

    /**
     * Delete method
     *
     * @param id the id
     * @return boolean value
     */
    boolean delete(long id);
}
