package com.epam.esm.validator;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.stereotype.Component;

/**
 * The type Tag validator.
 */
@Component
public class TagValidator implements Validator<TagClientModel> {
    private static final String INVALID_DATA = "invalid.tag";
    private static final String BAD_NAME = "bad.value.name";

    @Override
    public void validate(TagClientModel tag) {
        if (tag == null) {
            throw new ValidatorException(INVALID_DATA + "/tag=null");
        } else if (tag.getName() == null) {
            throw new ValidatorException(BAD_NAME + "/name=null");
        } else if (tag.getName().length() == 0){
            throw new ValidatorException(BAD_NAME + "/name's length=0");
        }
    }
}
