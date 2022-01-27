package com.epam.esm.validator;

import com.epam.esm.exception.ServiceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public interface Validator<T> {
    public void validate(T t) throws ServiceException;
}
