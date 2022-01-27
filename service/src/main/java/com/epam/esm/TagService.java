package com.epam.esm;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface TagService extends Service{
    TagClientModel addTag(TagEntity tag) throws ServiceException;

    List<TagClientModel> findAll();

    TagClientModel findTagById(long id) throws ServiceException;

    TagClientModel findTagByName(String name) throws ServiceException;

    boolean removeTag(TagEntity tag);
}
