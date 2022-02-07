package com.epam.esm;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public abstract class TagDao extends Dao<TagEntity> {
    /**
     * Add optional.
     *
     * @param tag the tag
     * @return the optional
     */
    public abstract Optional<TagEntity> add(TagEntity tag);

    /**
     * Find by certificate id list.
     *
     * @param id the id
     * @return the list
     */
    public abstract List<TagEntity> findByCertificateId(long id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    public abstract Optional<TagEntity> findByName(String name);
}
