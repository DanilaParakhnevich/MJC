package com.epam.esm.impl;

import com.epam.esm.CertificateDao;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.TagAttachedException;
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
    private static final String ATTACHED = "attached.tag";
    private static final String DUPLICATE = "duplicate.tag";
    private TagValidator validator;
    private TagModelMapper mapper;
    private CertificateDao certificateDao;
    private TagDao tagDao;


    @Override
    public TagClientModel add(TagClientModel tag) {
        validator.validate(tag);
        tagDao.add(mapper.toEntity(tag));
        return mapper
                .toClientModel(tagDao
                        .findByName(tag.getName()).get());
    }

    @Override
    public TagClientModel addIfNotExist(TagClientModel tag) {
        if (tag != null && !tagDao.findByName(tag.getName()).isPresent()) {
            return add(tag);
        }
        return tag != null ? findByName(tag.getName()) : null;
    }

    @Override
    public List<TagClientModel> findAll() {
        return tagDao.findAll().stream()
                .map(mapper::toClientModel)
                .collect(Collectors.toList());
    }

    @Override
    public TagClientModel findById(long id) {
        Optional<TagEntity> tag = tagDao.findById(id);
        if (tag.isPresent()) {
            return mapper.toClientModel(tag.get());
        }
        throw new UnknownTagException(UNKNOWN + "/id=" + id);
    }

    @Override
    public TagClientModel findByName(String name) {
        Optional<TagEntity> tag = tagDao.findByName(name);
        if (tag.isPresent()) {
            return mapper.toClientModel(tag.get());
        }
        throw new UnknownTagException(UNKNOWN + "/name=" + name);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Optional<TagEntity> tag = tagDao.findById(id);
        if (tag.isPresent() && !certificateDao.findByTagName(tag.get().getName()).isEmpty()) {
            throw new TagAttachedException(ATTACHED);
        }
        if (tag.isPresent()) {
            try {
                findByName(tag.get().getName());
            } catch (UnknownTagException e) {
                return tagDao.delete(id);
            }
            throw new DuplicateTagException(DUPLICATE);
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
        this.tagDao = tagDAO;
    }

    /**
     * Sets certificate dao.
     *
     * @param certificateDao the certificate dao
     */
    @Autowired
    public void setCertificateDao(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    /**
     * Sets mapper.
     *
     * @param mapper the mapper
     */
    @Autowired
    public void setMapper(TagModelMapper mapper) {
        this.mapper = mapper;
    }
}
