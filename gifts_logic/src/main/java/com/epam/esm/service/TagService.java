package com.epam.esm.service;

import com.epam.esm.bean.Tag;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface TagService extends Service{
    Tag addTag(Tag tag) throws ServiceException;

    List<Tag> findAll();

    Tag findTagById(long id) throws ServiceException;

    Tag findTagByName(String name) throws ServiceException;

    boolean removeTag(Tag tag);
}
