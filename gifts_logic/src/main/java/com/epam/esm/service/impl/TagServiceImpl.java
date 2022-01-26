package com.epam.esm.service.impl;

import com.epam.esm.bean.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final String TAG_ID_INCORRECT = "Tag with this id is not exist";
    private static final String TAG_NAME_INCORRECT = "Tag with this name is not exist";
    private final TagValidator validator;
    private final TagDAO tagDAO;

    @Autowired
    TagServiceImpl(TagDAO tagDAO, TagValidator validator) {
        this.tagDAO = tagDAO;
        this.validator = validator;
    }

    @Override
    public Tag addTag(Tag tag) throws ServiceException {
        validator.validate(tag);
        return tagDAO.add(tag).isPresent() ? tag : null;
    }

    @Override
    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public Tag findTagById(long id) throws ServiceException {
        Optional<Tag> tag = tagDAO.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        }
        throw new ServiceException(TAG_ID_INCORRECT);
    }

    @Override
    public Tag findTagByName(String name) throws ServiceException {
        Optional<Tag> tag = tagDAO.findByName(name);
        if (tag.isPresent()) {
            return tag.get();
        }
        throw new ServiceException(TAG_NAME_INCORRECT);
    }

    @Override
    public boolean removeTag(Tag tag) {
        return tagDAO.delete(tag);
    }
}
