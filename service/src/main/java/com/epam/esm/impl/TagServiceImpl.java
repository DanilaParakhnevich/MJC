package com.epam.esm.impl;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.TagDAO;
import com.epam.esm.TagService;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
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
    public TagClientModel addTag(TagEntity tag) throws ServiceException {
        validator.validate(tag);
        return tagDAO.add(tag).isPresent()
                ? TagMapper.INSTANCE.tagToTagClientModel(tag)
                : null;
    }

    @Override
    public List<TagClientModel> findAll() {
        return tagDAO.findAll().stream()
                .map(TagMapper.INSTANCE::tagToTagClientModel)
                .collect(Collectors.toList());
    }

    @Override
    public TagClientModel findTagById(long id) throws ServiceException {
        Optional<TagEntity> tag = tagDAO.findById(id);
        if (tag.isPresent()) {
            return TagMapper.INSTANCE.tagToTagClientModel(tag.get());
        }
        throw new ServiceException(TAG_ID_INCORRECT);
    }

    @Override
    public TagClientModel findTagByName(String name) throws ServiceException {
        Optional<TagEntity> tag = tagDAO.findByName(name);
        if (tag.isPresent()) {
            return TagMapper.INSTANCE.tagToTagClientModel(tag.get());
        }
        throw new ServiceException(TAG_NAME_INCORRECT);
    }

    @Override
    public boolean removeTag(TagEntity tag) {
        return tagDAO.delete(tag);
    }
}
