package com.epam.esm;

import com.epam.esm.entity.CertificateEntity;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * The interface Certificate dao.
 */
public interface CertificateDAO extends DAO {
    /**
     * Add optional.
     *
     * @param certificate the certificate
     * @return the optional
     * @throws ParseException the parse exception
     */
    Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
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
     * Find all list.
     *
     * @return the list
     */
    List<CertificateEntity> findAll();

    /**
     * Update boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     * @throws ParseException the parse exception
     */
    boolean update (CertificateEntity certificate) throws ParseException;

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteById (long id);

    /**
     * Add tag to certificate boolean.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     * @return the boolean
     */
    boolean addTagToCertificate(long certificateId, long tagId);

    /**
     * Clear tags by certificate boolean.
     *
     * @param certificateId the certificate id
     * @return the boolean
     */
    boolean clearTagsByCertificate(long certificateId);
}
