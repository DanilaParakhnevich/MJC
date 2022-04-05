package com.epam.esm.impl;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.TagService;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.exception.TagAttachedException;
import com.epam.esm.validator.exception.UnknownTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.handler.RequestParameter.PAGE;
import static com.epam.esm.handler.RequestParameter.PAGE_SIZE;

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
    private CertificateDao certificateRepository;
    private TagDao tagDao;


    @Override
    public TagClientModel add(TagClientModel tag) {
        validator.validate(tag);
        tagDao.create(mapper.toEntity(tag));
        return mapper
                .toClientModel(tagDao
                        .readByName(tag.getName()).get());
    }

    @Override
    public TagClientModel addIfNotExist(TagClientModel tag) {
        if (tag != null && !tagDao.readByName(tag.getName()).isPresent()) {
            return add(tag);
        }
        return tag != null ? readByName(tag.getName()) : null;
    }

    @Override
    public List<TagClientModel> readAll(Map<String, String> parameters) {
        if (!parameters.containsKey(PAGE) || !parameters.containsKey(PAGE_SIZE)) {
            throw new BadParameterException("bad.param");
        }
        return tagDao.readAll(Long.parseLong(parameters.get(PAGE)),
                        Long.parseLong(parameters.get(PAGE_SIZE)))
                .stream()
                .map(mapper::toClientModel)
                .collect(Collectors.toList());
    }

    @Override
    public TagClientModel readById(long id) {
        Optional<TagEntity> tag = tagDao.readById(id);
        if (tag.isPresent()) {
            return mapper.toClientModel(tag.get());
        }
        throw new UnknownTagException(UNKNOWN + "/id=" + id);
    }

    @Override
    public TagClientModel readByName(String name) {
        Optional<TagEntity> tag = tagDao.readByName(name);
        if (tag.isPresent()) {
            return mapper.toClientModel(tag.get());
        }
        throw new UnknownTagException(UNKNOWN + "/name=" + name);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<TagEntity> tag = tagDao.readById(id);
        if (tag.isPresent() && !certificateRepository.findAllByTag(tag.get().getName(), 1, 5).isEmpty()) {
            throw new TagAttachedException(ATTACHED);
        }
        if (tag.isPresent()) {
            tagDao.deleteById(tag.get().getId());
            return;
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
     * Sets certificate dao.
     *
     * @param certificateRepository the certificate dao
     */
    @Autowired
    public void setCertificateService(CertificateDao certificateRepository) {
        this.certificateRepository = certificateRepository;
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

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }
}
