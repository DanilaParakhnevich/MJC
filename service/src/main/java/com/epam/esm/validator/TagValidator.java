package com.epam.esm.validator;

import com.epam.esm.TagDao;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.validator.exception.DuplicateTagException;
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
     * The Tag dao.
     */
    TagDao tagDAO;

    @Override
    public void validate(TagClientModel tag) {
        if (tag == null) {
            throw new ValidatorException(INVALID_DATA + "/tag=null");
        } else if (tag.getName() == null) {
            throw new ValidatorException(BAD_NAME + "/name=null");
        } else if (tag.getName().length() == 0){
            throw new ValidatorException(BAD_NAME + "/name's length=0");
        } else if (tagDAO.findByName(tag.getName()).isPresent()) {
            throw new DuplicateTagException(DUPLICATE);
        }
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
