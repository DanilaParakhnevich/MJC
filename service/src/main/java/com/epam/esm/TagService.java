package com.epam.esm;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;

import com.epam.esm.validator.exception.UnknownTagException;

import java.util.List;

public interface TagService extends Service {
    TagClientModel add(TagEntity tag);

    TagClientModel addIfNotExist(TagEntity tag);

    List<TagClientModel> findAll();

    TagClientModel findTagById(long id);

    TagClientModel findTagByName(String name);

    boolean deleteById(long id) throws UnknownTagException;
}
