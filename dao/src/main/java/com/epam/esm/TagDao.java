package com.epam.esm;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao {
    /**
     * Add optional.
     *
     * @param tag the tag
     * @return the optional
     */
    Optional<TagEntity> add(TagEntity tag);

    /**
     * Find all method
     *
     * @return list of tag entity
     */
    List<TagEntity> findAll();

    /**
     * Find by certificate id list.
     *
     * @param id the id
     * @return the list
     */
    List<TagEntity> findByCertificateId(long id);

    /**
     * Find by id method
     *
     * @param id the id
     * @return the optional of tag entity
     */
    Optional<TagEntity> findById(long id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<TagEntity> findByName(String name);

    /**
     * Delete method
     *
     * @param id the id
     * @return boolean value
     */
    boolean delete(long id);
}
