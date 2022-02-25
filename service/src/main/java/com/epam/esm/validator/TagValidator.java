package com.epam.esm.validator;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.UnknownTagException;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Tag validator.
 */
@Component
public class TagValidator implements Validator<TagClientModel> {
    private static final String INVALID_DATA = "invalid.tag";
    private static final String DUPLICATE = "duplicate.tag";
    private static final String BAD_NAME = "bad.value.name";

    /**
     * The Tag service.
     */
    TagService tagService;

    @Override
    public void validate(TagClientModel tag) {
        if (tag == null) {
            throw new ValidatorException(INVALID_DATA + "/tag=null");
        } else if (tag.getName() == null) {
            throw new ValidatorException(BAD_NAME + "/name=null");
        } else if (tag.getName().length() == 0){
            throw new ValidatorException(BAD_NAME + "/name's length=0");
        }
        try {
            tagService.findByName(tag.getName());
        } catch (UnknownTagException e) {
            return;
        }
        throw new DuplicateTagException(DUPLICATE);
    }

    /**
     * Sets tag dao.
     *
     * @param tagService the tag dao
     */
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
