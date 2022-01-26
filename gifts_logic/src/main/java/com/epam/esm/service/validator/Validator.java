package com.epam.esm.service.validator;

import com.epam.esm.service.exception.ServiceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public interface Validator<T> {
    public void validate(T t) throws ServiceException;
}
