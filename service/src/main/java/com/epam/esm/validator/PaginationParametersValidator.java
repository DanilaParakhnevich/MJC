package com.epam.esm.validator;

import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.epam.esm.handler.RequestParameter.PAGE;
import static com.epam.esm.handler.RequestParameter.PAGE_SIZE;

@Component
public class PaginationParametersValidator implements Validator<Map<String, String>>{
    @Override
    public void validate(Map<String, String> parameters) throws ValidatorException {
        if (!parameters.containsKey(PAGE) || !parameters.containsKey(PAGE_SIZE)) {
            throw new BadParameterException("bad.param");
        }
    }
}
