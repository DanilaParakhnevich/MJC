package com.epam.esm;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDAO extends DAO {
    /**
     * Add optional.
     *
     * @param tag the tag
     * @return the optional
     */
    Optional<TagEntity> add(TagEntity tag);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<TagEntity> findById(long id);

    /**
     * Find by certificate id list.
     *
     * @param id the id
     * @return the list
     */
    List<TagEntity> findByCertificateId(long id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<TagEntity> findByName(String name);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TagEntity> findAll();

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete (long id);
}
