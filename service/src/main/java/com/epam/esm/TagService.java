package com.epam.esm;

import com.epam.esm.dto.TagClientModel;

import com.epam.esm.validator.exception.UnknownTagException;

import java.util.List;
import java.util.Map;

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
     * Find list by certificate id.
     * @param id certificate id
     *
     * @return the list
     */
    List<TagClientModel> readByCertificateId(long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TagClientModel> readAll(Map<String, String> parameters);

    /**
     * Find tag by id tag client model.
     *
     * @param id the id
     * @return the tag client model
     */
    TagClientModel readById(long id);

    /**
     * Find tag by name tag client model.
     *
     * @param name the name
     * @return the tag client model
     */
    TagClientModel readByName(String name);

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @throws UnknownTagException the unknown tag exception
     */
    void deleteById(long id) throws UnknownTagException;
}
