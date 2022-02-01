package com.epam.esm.validator;

import com.epam.esm.TagDAO;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.validator.exception.DuplicateTagException;
import com.epam.esm.validator.exception.InvalidTagDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TagValidator implements Validator<TagEntity>{
    private static final String INVALID_DATA = "invalid.tag";
    private static final String DUPLICATE = "duplicate.tag";

    @Autowired
    TagDAO tagDAO;

    @Override
    public void validate(TagEntity tag) {
        if (tag == null) {
            throw new InvalidTagDataException(INVALID_DATA);
        } else if (tag.getName() == null) {
            throw new InvalidTagDataException(INVALID_DATA);
        } else if (tagDAO.findByName(tag.getName()).isPresent()) {
            throw new DuplicateTagException(DUPLICATE);
        } else if (tag.getName().length() == 0){
            throw new InvalidTagDataException(INVALID_DATA);
        }
    }

    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }
}
