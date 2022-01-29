package com.epam.esm.handler;

import com.epam.esm.exception.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(value = ServiceException.class)
    public String handleServiceException(ServiceException e) {
        return e.getMessage();
    }
}
