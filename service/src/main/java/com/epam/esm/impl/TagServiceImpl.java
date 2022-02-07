package com.epam.esm.impl;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.mapper.TagClientModelMapper;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.UnknownTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {
    private static final String UNKNOWN = "nonexistent.tag";
    private TagValidator validator;
    private TagDao tagDAO;


    @Override
    public TagClientModel add(TagEntity tag) {
        validator.validate(tag);
        tagDAO.add(tag);
        return TagClientModelMapper.INSTANCE
                .tagToTagClientModel(tagDAO
                        .findByName(tag.getName()).get());
    }

    @Override
    public TagClientModel addIfNotExist(TagEntity tag) {
        if (tag != null && !tagDAO.findByName(tag.getName()).isPresent()) {
            return add(tag);
        }
        return tag != null ? findTagByName(tag.getName()) : null;
    }

    @Override
    public List<TagClientModel> findAll() {
        return tagDAO.findAll().stream()
                .map(TagClientModelMapper.INSTANCE::tagToTagClientModel)
                .collect(Collectors.toList());
    }

    @Override
    public TagClientModel findTagById(long id) {
        Optional<TagEntity> tag = tagDAO.findById(id);
        if (tag.isPresent()) {
            return TagClientModelMapper.INSTANCE.tagToTagClientModel(tag.get());
        }
        throw new UnknownTagException(UNKNOWN + "/id=" + id);
    }

    @Override
    public TagClientModel findTagByName(String name) {
        Optional<TagEntity> tag = tagDAO.findByName(name);
        if (tag.isPresent()) {
            return TagClientModelMapper.INSTANCE.tagToTagClientModel(tag.get());
        }
        throw new UnknownTagException(UNKNOWN + "/name=" + name);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        if (tagDAO.findById(id).isPresent()) {
            return tagDAO.delete(id);
        }
        throw new UnknownTagException(UNKNOWN + "/id=" + id);
    }

    /**
     * Sets validator.
     *
     * @param validator the validator
     */
    @Autowired
    public void setValidator(TagValidator validator) {
        this.validator = validator;
    }

    /**
     * Sets tag dao.
     *
     * @param tagDAO the tag dao
     */
    @Autowired
    public void setTagDAO(TagDao tagDAO) {
        this.tagDAO = tagDAO;
    }
}
