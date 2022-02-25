package com.epam.esm.validator;

import com.epam.esm.validator.exception.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The interface Validator.
 *
 * @param <T> the type parameter
 */
@Component
@Scope("singleton")
public interface Validator<T> {
    /**
     * Validate.
     *
     * @param t the t
     * @throws ValidatorException the validator exception
     */
    public void validate(T t) throws ValidatorException;
}
