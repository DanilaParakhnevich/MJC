package com.epam.esm.service.validator;

import com.epam.esm.bean.Tag;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<Tag>{
    @Override
    public void validate(Tag tag) throws ServiceException {
        if (tag.getName() == null) {
            throw new ServiceException("Tag's name cannot equals null");
        } else if (tag.getName().length() == 0){
            throw new ServiceException("Tag's name cannot be clear");
        }
    }
}
