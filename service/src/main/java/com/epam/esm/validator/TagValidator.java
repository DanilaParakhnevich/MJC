package com.epam.esm.validator;

import com.epam.esm.TagDAO;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.validator.exception.BadNameException;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.InvalidTagDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TagValidator implements Validator<TagEntity> {
    private static final String INVALID_DATA = "invalid.tag";
    private static final String DUPLICATE = "duplicate.tag";
    private static final String BAD_NAME = "bad.value.name";

    @Autowired
    TagDAO tagDAO;

    @Override
    public void validate(TagEntity tag) {
        if (tag == null) {
            throw new InvalidTagDataException(INVALID_DATA + "/tag=null");
        } else if (tag.getName() == null) {
            throw new BadNameException(BAD_NAME + "/name=null");
        } else if (tag.getName().length() == 0){
            throw new BadNameException(BAD_NAME + "/name's length=0");
        } else if (tagDAO.findByName(tag.getName()).isPresent()) {
            throw new DuplicateTagException(DUPLICATE);
        }
    }

    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }
}
