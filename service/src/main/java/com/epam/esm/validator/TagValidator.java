package com.epam.esm.validator;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TagValidator implements Validator<TagEntity>{
    @Override
    public void validate(TagEntity tag) throws ValidatorException {
        if (tag == null) {
            throw new ValidatorException("Tag cannot equals null");
        }
        if (tag.getName() == null) {
            throw new ValidatorException("Tag's name cannot equals null");
        } else if (tag.getName().length() == 0){
            throw new ValidatorException("Tag's name cannot be clear");
        }
    }
}
