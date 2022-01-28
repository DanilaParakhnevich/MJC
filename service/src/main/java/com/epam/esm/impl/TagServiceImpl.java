package com.epam.esm.impl;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.TagDAO;
import com.epam.esm.TagService;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagClientModelMapper;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.ValidatorException;
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
    @Autowired
    private TagValidator validator;
    @Autowired
    private TagDAO tagDAO;


    @Override
    public TagClientModel addTag(TagEntity tag) throws ServiceException {
        try {
            validator.validate(tag);
            return tagDAO.add(tag).isPresent()
                    ? TagClientModelMapper.INSTANCE.tagToTagClientModel(tag)
                    : null;
        }
        catch (ValidatorException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TagClientModel> findAll() {
        return tagDAO.findAll().stream()
                .map(TagClientModelMapper.INSTANCE::tagToTagClientModel)
                .collect(Collectors.toList());
    }

    @Override
    public TagClientModel findTagById(long id) throws ServiceException {
        Optional<TagEntity> tag = tagDAO.findById(id);
        if (tag.isPresent()) {
            return TagClientModelMapper.INSTANCE.tagToTagClientModel(tag.get());
        }
        throw new ServiceException(TAG_ID_INCORRECT);
    }

    @Override
    public TagClientModel findTagByName(String name) throws ServiceException {
        Optional<TagEntity> tag = tagDAO.findByName(name);
        if (tag.isPresent()) {
            return TagClientModelMapper.INSTANCE.tagToTagClientModel(tag.get());
        }
        throw new ServiceException(TAG_NAME_INCORRECT);
    }

    @Override
    public boolean removeTag(TagEntity tag) {
        return tagDAO.delete(tag);
    }

    public void setValidator(TagValidator validator) {
        this.validator = validator;
    }

    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }
}
