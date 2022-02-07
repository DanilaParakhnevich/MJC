package com.epam.esm;

import com.epam.esm.dto.TagClientModel;

import com.epam.esm.validator.exception.UnknownTagException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Add tag client model.
     *
     * @param tag the tag
     * @return the tag client model
     */
    TagClientModel add(TagClientModel tag);

    /**
     * Add if not exist tag client model.
     *
     * @param tag the tag
     * @return the tag client model
     */
    TagClientModel addIfNotExist(TagClientModel tag);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TagClientModel> findAll();

    /**
     * Find tag by id tag client model.
     *
     * @param id the id
     * @return the tag client model
     */
    TagClientModel findTagById(long id);

    /**
     * Find tag by name tag client model.
     *
     * @param name the name
     * @return the tag client model
     */
    TagClientModel findTagByName(String name);

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws UnknownTagException the unknown tag exception
     */
    boolean deleteById(long id) throws UnknownTagException;
}
