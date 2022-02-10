package com.epam.esm;

import com.epam.esm.entity.CertificateEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * The interface Certificate dao.
 */
public abstract class CertificateDao extends Dao<CertificateEntity> {
    /**
     * Add optional.
     *
     * @param certificate the certificate
     * @return the optional of certificate entity
     * @throws ParseException the parse exception
     */
    public abstract Optional<CertificateEntity> add(CertificateEntity certificate) throws ParseException;

    /**
     * Find by name part list.
     *
     * @param namePart the name part
     * @return the list
     */
    public abstract List<CertificateEntity> findByNamePart(String namePart);

    /**
     * Find by tag name list.
     *
     * @param name the name
     * @return the list
     */
    public abstract List<CertificateEntity> findByTagName(String name);

    /**
     * Update boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     * @throws ParseException the parse exception
     */
    public abstract boolean update (CertificateEntity certificate) throws ParseException;

    /**
     * Add tag to certificate boolean.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     * @return the boolean
     */
    public abstract boolean addTagToCertificate(long certificateId, long tagId);

    /**
     * Delete tag from certificate boolean.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     * @return the boolean
     */
    public abstract boolean deleteTagFromCertificate(long certificateId, long tagId);

    /**
     * Clear tags by certificate boolean.
     *
     * @param certificateId the certificate id
     * @return the boolean
     */
    public abstract boolean clearTagsByCertificate(long certificateId);
}
