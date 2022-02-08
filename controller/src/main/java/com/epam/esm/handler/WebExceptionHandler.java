package com.epam.esm.handler;

import com.epam.esm.handler.error.ErrorCode;
import com.epam.esm.handler.error.ErrorResponse;
import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.handler.translator.Translator;
import com.epam.esm.validator.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The type Web exception handler.
 */
@RestControllerAdvice
public class WebExceptionHandler {
    private Translator translator;

    /**
     * Handle response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(DuplicateTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(DuplicateTagException e) {
        return new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.DUPLICATE_TAG.getCode()),
                translator.translate(e.getMessage()));
    }

    /**
     * Handle response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(UnknownTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(UnknownTagException e) {
        return new ErrorResponse(
                concatenate(HttpStatus.NOT_FOUND.value(), ErrorCode.NONEXISTENT_TAG.getCode()),
                getFullMessage(e.getMessage()));
    }

    /**
     * Handle response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(DuplicateCertificateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(DuplicateCertificateException e) {
        return new ErrorResponse(ErrorCode.DUPLICATE_CERTIFICATE.getCode(),
                translator.translate(e.getMessage()));
    }

    /**
     * Handle response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(UnknownCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(UnknownCertificateException e) {
        return new ErrorResponse(
                concatenate(HttpStatus.NOT_FOUND.value(), ErrorCode.NONEXISTENT_CERTIFICATE.getCode()),
                getFullMessage(e.getMessage()));
    }

    /**
     * Handle response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(ValidatorException e) {
        return new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_VALUE.getCode()),
                getFullMessage(e.getMessage()));
    }

    /**
     * Handle response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(NumberFormatException e) {
        return new ErrorResponse(
               HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
    }

    /**
     * Handle http message not readable exception response entity.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handle(HttpMessageNotReadableException ex)
    {
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        return new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_VALUE.getCode()),
                translator.translate("bad.request.value") +
                        " (" + translator.translate("field") + " = " +
                        translator.translate("field." + jme.getPath().get(0).getFieldName()) +
                        ")");
    }

    /**
     * Handle bad parameter response entity.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(BadParameterException.class)
    public ErrorResponse handle(BadParameterException ex)
    {
        return new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_PARAM.getCode()),
                translator.translate(ex.getMessage()));
    }


    private String getFullMessage(String errorMessage) {
        return translator.translate(errorMessage.split("/")[0]) +
                getAdvanceToMessage(errorMessage
                        .split("/")[1]);
    }

    private String getAdvanceToMessage(String partOfError) {
        String finalPart = partOfError.split("=")[0];
        finalPart = "(" + finalPart + " = ";
        return " " + finalPart + partOfError.split("=")[1] + ")";
    }

    private long concatenate (long first, long second) {
        return Long.parseLong(String.valueOf(first) + String.valueOf(second));
    }

    /**
     * Sets translator.
     *
     * @param translator the translator
     */
    @Autowired
    public void setTranslator(Translator translator) {
        this.translator = translator;
    }
}
