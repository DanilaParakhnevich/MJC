package com.epam.esm.validator;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<TagEntity>{
    @Override
    public void validate(TagEntity tag) throws ServiceException {
        if (tag.getName() == null) {
            throw new ServiceException("Tag's name cannot equals null");
        } else if (tag.getName().length() == 0){
            throw new ServiceException("Tag's name cannot be clear");
        }
    }
}
